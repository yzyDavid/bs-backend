package bs.controllers;

import bs.annotations.Authorization;
import bs.annotations.CurrentUser;
import bs.configs.Config;
import bs.entities.*;
import bs.repositories.LogRepository;
import bs.repositories.UserStudyingWordRepository;
import bs.repositories.WordRepository;
import bs.repositories.WordbookRepository;
import bs.requests.AddWordbookRequest;
import bs.requests.FinishWordRequest;
import bs.responses.StatsResponse;
import bs.responses.WordRepresentation;
import bs.responses.WordsResponse;
import bs.services.LogService;
import bs.services.StudyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author yzy
 * for study relative operations when a user is logged in.
 */
@Controller
@RequestMapping(path = "/study")
public class StudyController {
    private Log logger;

    private final UserStudyingWordRepository userStudyingWordRepository;
    private final WordRepository wordRepository;
    private final WordbookRepository wordbookRepository;
    private final StudyService studyService;
    private final LogRepository logRepository;
    private final LogService logService;

    @Autowired
    public StudyController(UserStudyingWordRepository userStudyingWordRepository,
                           WordRepository wordRepository,
                           WordbookRepository wordbookRepository,
                           StudyService studyService,
                           LogRepository logRepository,
                           LogService logService
    ) {
        this.studyService = studyService;
        this.logRepository = logRepository;
        this.logService = logService;
        this.logger = LogFactory.getLog(this.getClass());
        this.userStudyingWordRepository = userStudyingWordRepository;
        this.wordRepository = wordRepository;
        this.wordbookRepository = wordbookRepository;
    }

    /**
     * @param user
     * @return get words the user need to study today.
     */
    @Authorization
    @GetMapping(path = "/today")
    ResponseEntity<WordsResponse> today(@CurrentUser UserEntity user) {
        ArrayList<WordEntity> wordList = new ArrayList<>();
        Iterable<UserStudyingWordRelation> relations = userStudyingWordRepository.findAllByUserId(user.getId());
        int totalCount = Config.MAX_BATCH_TODAY_WORDS;
        for (UserStudyingWordRelation relation : relations) {
            if (totalCount == 0) {
                break;
            }
            if (!relation.shouldBeStudiedToday()) {
                continue;
            }
            if (relation.isStudied()) {
                continue;
            }
            long wordId = relation.getWordId();
            // TODO: need check existing?
            WordEntity word = wordRepository.getById(wordId);
            wordList.add(word);
            --totalCount;
        }
        ArrayList<WordRepresentation> retList = new ArrayList<>();
        for (WordEntity entity : wordList) {
            retList.add(new WordRepresentation(entity));
        }
        return new ResponseEntity<>(new WordsResponse(retList), HttpStatus.OK);
    }

    @Authorization
    @PostMapping(path = "/today")
    ResponseEntity todayCheck(@CurrentUser UserEntity user) {
        LogEntity log = logService.getLogEntityOfTodayByUser(user);
        log.setReachTarget(true);
        logRepository.save(log);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Authorization
    @PostMapping(path = "/finish_word")
    ResponseEntity finishWord(@RequestBody FinishWordRequest request, @CurrentUser UserEntity user) {
        String wordName = request.getWord();
        if (!wordRepository.existsByWord(wordName)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        WordEntity word = wordRepository.getByWord(wordName);
        UserStudyingWordRelation relation = userStudyingWordRepository.findByUserIdAndWordId(user.getId(), word.getId());
        relation.setStudied(true);
        userStudyingWordRepository.save(relation);

        LogEntity log = logService.getLogEntityOfTodayByUser(user);
        log.setStudyCount(log.getStudyCount() + 1);
        logRepository.save(log);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @param user
     * @return get stats of the user.
     * including numbers of words he/she added, studied and queued.
     * TODO
     */
    @Authorization
    @GetMapping(path = "/stats")
    public ResponseEntity<StatsResponse> stats(@CurrentUser UserEntity user) {
        Collection<WordEntity> wordsStudying = user.getWordsStudying();
        long totalWords = wordsStudying.size();
        long toStudyWords = 0, studiedWords = 0;
        Iterable<UserStudyingWordRelation> relations = userStudyingWordRepository.findAllByUserId(user.getId());
        for (UserStudyingWordRelation relation : relations) {
            if (relation.getRank() == 0) {
                ++studiedWords;
            } else {
                ++toStudyWords;
            }
        }
        long todayToStudyWords = studyService.wordsToStudyTodayFor(user);
        long recordDays = logService.getCheckedDaysByUser(user);
        return new ResponseEntity<>(new StatsResponse(totalWords, toStudyWords, studiedWords, 0, todayToStudyWords, recordDays), HttpStatus.OK);
    }

    /**
     * @param user
     * @return add a whole wordbook to my to-study list.
     * TODO: testing && make put_word action like this.
     */
    @Authorization
    @PutMapping(path = "/wordbook")
    public ResponseEntity wordbook(@CurrentUser UserEntity user, @RequestBody AddWordbookRequest request) {
        String wordbookName = request.getWordbook();
        if (!wordbookRepository.existsByWordbookName(wordbookName)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        WordbookEntity wordbook = wordbookRepository.findByWordbookName(wordbookName);
        ArrayList<WordEntity> wordsToAdd = new ArrayList<>(wordbook.getWords());
        Iterable<UserStudyingWordRelation> relations = userStudyingWordRepository.findAllByUserId(user.getId());
        int maxRank = 30, maxCount = 0;
        for (UserStudyingWordRelation relation : relations) {
            if (relation.getRank() > maxRank) {
                maxRank = relation.getRank();
                maxCount = 0;
            }
            if (relation.getRank() == maxRank) {
                ++maxCount;
            }
        }
        Collections.shuffle(wordsToAdd);
        for (WordEntity word : wordsToAdd) {
            if (userStudyingWordRepository.existsByUserIdAndWordId(user.getId(), word.getId())) {
                continue;
            }
            if (maxCount >= Config.WORDS_PER_DAY) {
                ++maxRank;
                maxCount = 0;
            }
            UserStudyingWordRelation relation = new UserStudyingWordRelation();
            relation.setUserId(user.getId());
            relation.setWordId(word.getId());
            relation.setStudied(false);
            relation.setRank(maxRank);
            userStudyingWordRepository.save(relation);
            ++maxCount;
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
