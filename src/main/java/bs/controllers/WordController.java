package bs.controllers;

import bs.entities.WordEntity;
import bs.entities.WordbookEntity;
import bs.repositories.WordRepository;
import bs.repositories.WordbookRepository;
import bs.annotations.Authorization;
import bs.annotations.CurrentUser;
import bs.requests.AddWordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

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
    public ResponseEntity addWord(@RequestBody AddWordRequest addWordRequest, @CurrentUser CurrentUser user) {
        if (wordRepository.existsByWord(addWordRequest.getWord())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ArrayList<WordbookEntity> wordbooks = new ArrayList<>();
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
}
