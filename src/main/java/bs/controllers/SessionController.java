package bs.controllers;

import bs.annotations.Authorization;
import bs.annotations.CurrentUser;
import bs.entities.SessionEntity;
import bs.entities.UserEntity;
import bs.repositories.SqlSessionRepository;
import bs.repositories.UserRepository;
import bs.requests.LoginRequest;
import bs.responses.LoginResponse;
import bs.utils.SecurityUtils;
import bs.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author yzy
 * <p>
 * RESTful APIs for login(creating), log out tokens, etc.
 */
@Controller
@RequestMapping(path = "/session")
public class SessionController {
    private final UserRepository userRepository;

    private final SqlSessionRepository sqlSessionRepository;

    @Autowired
    public SessionController(UserRepository userRepository, SqlSessionRepository sqlSessionRepository) {
        this.userRepository = userRepository;
        this.sqlSessionRepository = sqlSessionRepository;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) {
        if (!userRepository.existsByEmail(login.getEmail())) {
            return new ResponseEntity<>(new LoginResponse("", "", "", "User not exists"), HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userRepository.findByEmail(login.getEmail());
        if (!user.getHashedPassword().equals(SecurityUtils.getHashedPasswordByPasswordAndSalt(login.getPassword(), user.getSalt()))) {
            return new ResponseEntity<>(new LoginResponse("", "", user.getName(), "Password incorrect"), HttpStatus.BAD_REQUEST);
        }

        SessionEntity session = new SessionEntity();
        session.setEmail(login.getEmail());
        session.setToken(SessionUtils.getToken());
        session.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        if (sqlSessionRepository.existsByEmail(login.getEmail())) {
            // TODO: process with duplicated insert.
            SessionEntity sessionToRemove = sqlSessionRepository.findByEmail(login.getEmail());
            sqlSessionRepository.delete(sessionToRemove);
        }

        sqlSessionRepository.save(session);

        return new ResponseEntity<>(new LoginResponse(login.getEmail(), session.getToken(), user.getName(), "OK"), HttpStatus.OK);
    }

    /**
     * TODO
     *
     * @param user
     * @return
     */
    @PostMapping(path = "logout")
    @Authorization
    public ResponseEntity logout(@CurrentUser UserEntity user) {
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
