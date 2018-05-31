package bs.controllers;

import bs.annotations.Authorization;
import bs.entities.WordbookEntity;
import bs.repositories.WordbookRepository;
import bs.requests.AddWordbookRequest;
import bs.responses.WordbookRepresentation;
import bs.responses.WordbookResponse;
import bs.responses.WordsOfWordbookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yzy
 */
@Controller
@RequestMapping(path = "/wordbook")
public class WordbookController {
    private final WordbookRepository wordbookRepository;

    @Autowired
    public WordbookController(WordbookRepository wordbookRepository) {
        this.wordbookRepository = wordbookRepository;
    }

    @Authorization
    @PutMapping
    public ResponseEntity addWordbook(@RequestBody AddWordbookRequest request) {
        if (!wordbookRepository.existsByWordbookName(request.getWordbook())) {
            WordbookEntity wordbookEntity = new WordbookEntity();
            // NO set associated words.
            wordbookEntity.setWordbookName(request.getWordbook());
            wordbookRepository.save(wordbookEntity);
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Authorization
    @GetMapping(path = "/all")
    public ResponseEntity<WordbookResponse> getWordbooks() {
        Collection<WordbookEntity> all = wordbookRepository.findAll();
        ArrayList<WordbookRepresentation> representations = new ArrayList<>();
        for (WordbookEntity entity : all) {
            representations.add(new WordbookRepresentation(entity));
        }
        return new ResponseEntity<>(new WordbookResponse(representations), HttpStatus.OK);
    }

    @Authorization
    @GetMapping(path = "/words")
    public ResponseEntity<WordsOfWordbookResponse> wordsOfWordbook(@RequestParam(name = "wordbook") String wordbookName) {
        if (!wordbookRepository.existsByWordbookName(wordbookName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        WordbookEntity wordbook = wordbookRepository.findByWordbookName(wordbookName);
        return new ResponseEntity<>(new WordsOfWordbookResponse(wordbook.getWords()), HttpStatus.OK);
    }
}
