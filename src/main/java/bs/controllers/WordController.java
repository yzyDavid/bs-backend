package bs.controllers;

import bs.annotations.Authorization;
import bs.annotations.CurrentUser;
import bs.configs.Config;
import bs.entities.UserEntity;
import bs.entities.WordEntity;
import bs.entities.WordbookEntity;
import bs.repositories.WordRepository;
import bs.repositories.WordbookRepository;
import bs.requests.AddWordRequest;
import bs.responses.WordRepresentation;
import bs.responses.WordsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yzy
 */
@Controller
@RequestMapping(path = "/word")
public class WordController {
    private final WordRepository wordRepository;

    private final WordbookRepository wordbookRepository;

    @Autowired
    public WordController(WordRepository wordRepository, WordbookRepository wordbookRepository) {
        this.wordRepository = wordRepository;
        this.wordbookRepository = wordbookRepository;
    }

    @Authorization
    @PutMapping
    public ResponseEntity addWord(@RequestBody AddWordRequest addWordRequest, @CurrentUser UserEntity user) {
        if (wordRepository.existsByWord(addWordRequest.getWord())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ArrayList<WordbookEntity> wordbooks = new ArrayList<>();
        if (addWordRequest.getWordbooks() == null || addWordRequest.getWordbooks().size() == 0) {
            ArrayList<String> self = new ArrayList<>();
            self.add(Config.USER_WORDBOOK_PREFIX + user.getName());
            addWordRequest.setWordbooks(self);
        }
        for (String wordbook : addWordRequest.getWordbooks()) {
            if (!wordbookRepository.existsByWordbookName(wordbook)) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            wordbooks.add(wordbookRepository.findByWordbookName(wordbook));
        }

        WordEntity wordEntity = new WordEntity();

        wordEntity.setWord(addWordRequest.getWord());
        wordEntity.setWordbooks(wordbooks);
        wordEntity.setMeaning(addWordRequest.getMeaning());

        wordRepository.save(wordEntity);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * all words the user is studying.
     *
     * @param user
     * @return
     */
    @Authorization
    @GetMapping(path = "/my")
    public ResponseEntity<WordsResponse> myWord(@CurrentUser UserEntity user) {
        Collection<WordEntity> wordsStudying = user.getWordsStudying();
        ArrayList<WordRepresentation> retList = new ArrayList<>();
        for (WordEntity entity : wordsStudying) {
            retList.add(new WordRepresentation(entity));
        }
        return new ResponseEntity<>(new WordsResponse(retList), HttpStatus.OK);
    }
}
