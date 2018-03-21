package bs.word;

import bs.session.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yzy
 */
@Controller
@RequestMapping(path = "/wordbook")
public class WordbookController {
    private final WordbookRepository wordbookRepository;

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
}
