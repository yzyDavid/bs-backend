package bs.study;

import bs.session.Authorization;
import bs.session.CurrentUser;
import bs.user.UserEntity;
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

    @Authorization
    @GetMapping(path = "/today")
    TodayResponse today(@CurrentUser UserEntity user) {
        // TODO:
        return new TodayResponse(new ArrayList<>());
    }

    @Authorization
    @PostMapping(path = "/finish_word")
    FinishWordResponse finishWord(@RequestBody FinishWordRequest request, @CurrentUser UserEntity user) {
        // TODO:
        return new FinishWordResponse();
    }
}
