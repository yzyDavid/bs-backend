package bs.repositories;

import bs.entities.UserStudyingWordRelation;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
@SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
public interface UserStudyingWordRepository extends CrudRepository<UserStudyingWordRelation, Long> {
    Iterable<UserStudyingWordRelation> findAllByUserId(long userId);
}
