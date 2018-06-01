package bs.repositories;

import bs.entities.UserStudyingWordRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author yzy
 */
@SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
public interface UserStudyingWordRepository extends CrudRepository<UserStudyingWordRelation, Long> {
    /**
     * @param userId
     * @return
     */
    Iterable<UserStudyingWordRelation> findAllByUserId(long userId);

    /**
     * @param userId
     * @param wordId
     * @return
     * TODO: test
     */
    UserStudyingWordRelation findByUserIdAndWordId(long userId, long wordId);

    boolean existsByUserIdAndWordId(long userId, long wordId);
}
