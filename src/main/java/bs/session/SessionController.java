package bs.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import bs.user.UserEntity;
import bs.user.UserRepository;
import bs.utils.SecurityUtils;
import bs.utils.SessionUtils;

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
        if (!userRepository.existsByEmail(login.getUid())) {
            return new ResponseEntity<>(new LoginResponse("", "", "User not exists"), HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userRepository.findByEmail(login.getUid());
        if (!user.getHashedPassword().equals(SecurityUtils.getHashedPasswordByPasswordAndSalt(login.getPassword(), user.getSalt()))) {
            return new ResponseEntity<>(new LoginResponse("", "", "Password incorrect"), HttpStatus.BAD_REQUEST);
        }

        SessionEntity session = new SessionEntity();
        session.setEmail(login.getUid());
        session.setToken(SessionUtils.getToken());
        session.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        if (sqlSessionRepository.existsByEmail(login.getUid())) {
            // TODO: process with duplicated insert.
            SessionEntity sessionToRemove = sqlSessionRepository.findByEmail(login.getUid());
            sqlSessionRepository.delete(sessionToRemove);
        }

        sqlSessionRepository.save(session);

        return new ResponseEntity<>(new LoginResponse(login.getUid(), session.getToken(), "OK"), HttpStatus.OK);
    }
}
