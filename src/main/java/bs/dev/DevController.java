package bs.dev;

import bs.configs.Config;
import bs.study.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yzy
 * <p>
 * APIs for dev debug ONLY.
 */
@RequestMapping(path = "/dev")
@Controller
public class DevController {
    private final StudyService studyService;

    @Autowired
    public DevController(StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping(path = "plus_one_day")
    void plusOneDay(@RequestParam(name = "key") String devKey) {
        if (!devKey.equals(Config.DEV_KEY)) {
            return;
        }
        studyService.plusOneDay();
    }
}
