package bs.controllers;

import bs.configs.Config;
import bs.entities.UserEntity;
import bs.entities.WordbookEntity;
import bs.repositories.UserRepository;
import bs.repositories.WordbookRepository;
import bs.requests.AddUserRequest;
import bs.responses.AddUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static bs.utils.SecurityUtils.getHashedPasswordByPasswordAndSalt;
import static bs.utils.SecurityUtils.getSalt;

/**
 * @author yzy
 * <p>
 * For management of User register, delete and query, etc.
 * NOT for sessions.
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository userRepository;
    private final WordbookRepository wordbookRepository;

    @Autowired
    public UserController(UserRepository userRepository, WordbookRepository wordbookRepository) {
        this.userRepository = userRepository;
        this.wordbookRepository = wordbookRepository;
    }

    @PutMapping(path = "")
    public ResponseEntity<AddUserResponse> add(@RequestBody AddUserRequest user) {
        String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            return new ResponseEntity<>(new AddUserResponse("failed with duplicated email", "", ""), HttpStatus.BAD_REQUEST);
        }
        String name = user.getUsername();
        if (userRepository.existsByName(name)) {
            return new ResponseEntity<>(new AddUserResponse("failed with duplicated user name", "", ""), HttpStatus.BAD_REQUEST);
        }

        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        entity.setName(user.getUsername());
        String salt = getSalt();
        String hashedPassword = getHashedPasswordByPasswordAndSalt(user.getPassword(), salt);
        entity.setSalt(salt);
        entity.setHashedPassword(hashedPassword);

        String wordbookName = Config.USER_WORDBOOK_PREFIX + user.getUsername();
        WordbookEntity userBook = new WordbookEntity();
        userBook.setWordbookName(wordbookName);
        wordbookRepository.save(userBook);

        userRepository.save(entity);
        return new ResponseEntity<>(new AddUserResponse("OK", email, user.getUsername()), HttpStatus.CREATED);
    }
}
