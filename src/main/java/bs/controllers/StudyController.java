package bs.controllers;

import bs.annotations.Authorization;
import bs.annotations.CurrentUser;
import bs.entities.UserEntity;
import bs.entities.UserStudyingWordRelation;
import bs.entities.WordEntity;
import bs.repositories.UserStudyingWordRepository;
import bs.repositories.WordRepository;
import bs.requests.FinishWordRequest;
import bs.responses.FinishWordResponse;
import bs.responses.TodayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * @author yzy
 * for study relative operations when a user is logged in.
 */
@RequestMapping(path = "/study")
@Controller
public class StudyController {
    private final UserStudyingWordRepository userStudyingWordRepository;
    private final WordRepository wordRepository;

    @Autowired
    public StudyController(UserStudyingWordRepository userStudyingWordRepository, WordRepository wordRepository) {
        this.userStudyingWordRepository = userStudyingWordRepository;
        this.wordRepository = wordRepository;
    }

    /**
     * @param user
     * @return
     *
     * get words the user need to study today.
     */
    @Authorization
    @GetMapping(path = "/today")
    TodayResponse today(@CurrentUser UserEntity user) {
        ArrayList<WordEntity> wordList = new ArrayList<>();
        Iterable<UserStudyingWordRelation> relations = userStudyingWordRepository.findAllByUserId(user.getId());
        for (UserStudyingWordRelation relation : relations) {
            if (relation.isStudied()) {
                continue;
            }
            long wordId = relation.getWordId();
            // TODO: need check existing?
            WordEntity word = wordRepository.getById(wordId);
            wordList.add(word);
        }
        return new TodayResponse(wordList);
    }

    @Authorization
    @PostMapping(path = "/finish_word")
    FinishWordResponse finishWord(@RequestBody FinishWordRequest request, @CurrentUser UserEntity user) {
        // TODO:
        return new FinishWordResponse();
    }

    /**
     * @param user
     * @return
     *
     * get stats of the user.
     * including numbers of words he/she added, studied and queued.
     * TODO
     */
    @Authorization
    @GetMapping(path = "/stats")
    public ResponseEntity stats(@CurrentUser UserEntity user) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
