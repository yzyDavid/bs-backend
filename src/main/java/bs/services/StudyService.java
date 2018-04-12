package bs.services;

import bs.configs.Config;
import bs.entities.UserEntity;
import bs.repositories.UserRepository;
import bs.entities.WordEntity;
import bs.repositories.UserStudyingWordRepository;
import bs.entities.UserStudyingWordRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
     *
     * TODO: check is modified into DB.
     */
    public void plusOneDay() {
        Iterable<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            Collection<WordEntity> wordsStudying = user.getWordsStudying();
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
            }
        }
    }
}
