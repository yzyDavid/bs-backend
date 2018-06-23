package bs.services;

import bs.configs.Config;
import bs.entities.UserEntity;
import bs.entities.UserStudyingWordRelation;
import bs.repositories.UserRepository;
import bs.repositories.UserStudyingWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yzy
 */
@Service
public class StudyService {
    private final UserRepository userRepository;
    private final UserStudyingWordRepository userStudyingWordRepository;

    @Autowired
    public StudyService(UserRepository userRepository, UserStudyingWordRepository userStudyingWordRepository) {
        this.userRepository = userRepository;
        this.userStudyingWordRepository = userStudyingWordRepository;
    }

    /**
     * minus all rank counters.
     * <p>
     * A 0 rank means the word is finished.
     */
    public void plusOneDay() {
        Iterable<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            plusOneDayFor(user);
        }
    }

    public void plusOneDayFor(UserEntity user) {
        Iterable<UserStudyingWordRelation> relations = userStudyingWordRepository.findAllByUserId(user.getId());
        for (UserStudyingWordRelation relation : relations) {
            int rank = relation.getRank();
            if (Config.RECALL_DAYS.contains(rank)) {
                // need to study it today
                if (relation.isStudied()) {
                    relation.setRank(rank - 1);
                }
            } else {
                // no need, directly minus.
                relation.setRank(rank - 1);
            }
            relation.setStudied(false);
            userStudyingWordRepository.save(relation);
        }
    }

    public long wordsToStudyTodayFor(UserEntity user) {
        long retval = 0;
        Iterable<UserStudyingWordRelation> relations = userStudyingWordRepository.findAllByUserId(user.getId());
        for (UserStudyingWordRelation relation : relations) {
            if (!relation.shouldBeStudiedToday()) {
                continue;
            }
            if (relation.isStudied()) {
                continue;
            }
            ++retval;
        }
        return retval;
    }
}
