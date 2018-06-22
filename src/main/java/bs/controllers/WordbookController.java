package bs.controllers;

import bs.annotations.Authorization;
import bs.annotations.CurrentUser;
import bs.configs.Config;
import bs.entities.UserEntity;
import bs.entities.WordEntity;
import bs.entities.WordbookEntity;
import bs.repositories.WordbookRepository;
import bs.requests.AddWordbookRequest;
import bs.responses.WordRepresentation;
import bs.responses.WordbookRepresentation;
import bs.responses.WordbookResponse;
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
        ArrayList<WordbookRepresentation> rep = new ArrayList<>();
        for (WordbookEntity entity : all) {
            if (entity.getWordbookName().startsWith(Config.USER_WORDBOOK_PREFIX)) {
                continue;
            }
            rep.add(new WordbookRepresentation(entity));
        }
        return new ResponseEntity<>(new WordbookResponse(rep), HttpStatus.OK);
    }

    /**
     * TODO: replace with AddWordbookRequest
     * <p>
     * get words of a wordbook.
     * if the wordbookName is not present in the request body, return the users user define book.
     *
     * @param
     * @return
     */
    @Authorization
    @GetMapping(path = "/words")
    public ResponseEntity<WordsResponse> wordsOfWordbook(@RequestBody AddWordbookRequest request, @CurrentUser UserEntity user) {
        String wordbookName = request.getWordbook();
        if ((wordbookName == null) || "".equals(wordbookName)) {
            wordbookName = Config.USER_WORDBOOK_PREFIX + user.getName();
        }
        if (!wordbookRepository.existsByWordbookName(wordbookName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        WordbookEntity wordbook = wordbookRepository.findByWordbookName(wordbookName);
        ArrayList<WordRepresentation> retList = new ArrayList<>();
        for (WordEntity entity : wordbook.getWords()) {
            retList.add(new WordRepresentation(entity));
        }
        return new ResponseEntity<>(new WordsResponse(retList), HttpStatus.OK);
    }
}
