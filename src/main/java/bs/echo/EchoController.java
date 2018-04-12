package bs.echo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bs.entities.UserEntity;
import bs.annotations.Authorization;
import bs.annotations.CurrentUser;

/**
 * @author yzy
 */
@RestController
@RequestMapping(path = "/echo")
public class EchoController {
    @GetMapping("")
    public String echo() {
        return "Team A TSS is running.";
    }

    @GetMapping("/auth")
    @Authorization
    public String auth(@CurrentUser UserEntity user) {
        return "Login as " + user.getEmail();
    }
}
