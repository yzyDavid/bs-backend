package bs.controllers;

import bs.annotations.Authorization;
import bs.annotations.CurrentUser;
import bs.configs.Config;
import bs.entities.UserEntity;
import bs.entities.UserStudyingWordRelation;
import bs.entities.WordEntity;
import bs.entities.WordbookEntity;
import bs.repositories.UserStudyingWordRepository;
import bs.repositories.WordRepository;
import bs.repositories.WordbookRepository;
import bs.requests.AddWordbookRequest;
import bs.requests.FinishWordRequest;
import bs.responses.TodayResponse;
import bs.responses.WordRepresentation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

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

    @Autowired
    public StudyController(UserStudyingWordRepository userStudyingWordRepository, WordRepository wordRepository, WordbookRepository wordbookRepository) {
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
    ResponseEntity<TodayResponse> today(@CurrentUser UserEntity user) {
        logger.info("/study/today");
        ArrayList<WordEntity> wordList = new ArrayList<>();
        Iterable<UserStudyingWordRelation> relations = userStudyingWordRepository.findAllByUserId(user.getId());
        for (UserStudyingWordRelation relation : relations) {
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
        }
        ArrayList<WordRepresentation> retList = new ArrayList<>();
        for (WordEntity entity : wordList) {
            retList.add(new WordRepresentation(entity));
        }
        return new ResponseEntity<>(new TodayResponse(retList), HttpStatus.OK);
    }

    @Authorization
    @PostMapping(path = "/finish_word")
    ResponseEntity finishWord(@RequestBody FinishWordRequest request, @CurrentUser UserEntity user) {
        String wordName = request.getWord();
        if (!wordRepository.existsByWord(wordName)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        WordEntity word = wordRepository.getByWord(wordName);
        UserStudyingWordRelation relation = userStudyingWordRepository.findByUserIdAndWordId(user.getId(), word.getId());
        relation.setStudied(true);
        userStudyingWordRepository.save(relation);
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
    public ResponseEntity stats(@CurrentUser UserEntity user) {
        return new ResponseEntity(HttpStatus.OK);
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
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        WordbookEntity wordbook = wordbookRepository.findByWordbookName(wordbookName);
        Collection<WordEntity> wordsToAdd = wordbook.getWords();
        Iterable<UserStudyingWordRelation> relations = userStudyingWordRepository.findAllByUserId(user.getId());
        int maxRank = 0, maxCount = 0;
        for (UserStudyingWordRelation relation : relations) {
            if (relation.getRank() > maxRank) {
                maxRank = relation.getRank();
                maxCount = 0;
            }
            if (relation.getRank() == maxRank) {
                ++maxCount;
            }
        }
        for (WordEntity word : wordsToAdd) {
            if (maxCount >= Config.WORDS_PER_DAY) {
                ++maxRank;
                maxCount = 0;
            }
            UserStudyingWordRelation relation = new UserStudyingWordRelation();
            relation.setUserId(user.getId());
            relation.setWordId(word.getId());
            relation.setStudied(false);
            relation.setRank(maxRank);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
