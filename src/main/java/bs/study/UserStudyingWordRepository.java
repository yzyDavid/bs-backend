package bs.study;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface UserStudyingWordRepository extends CrudRepository<UserStudyingWordRelation, Long> {
    Iterable<UserStudyingWordRelation> findAllByUserId(long userId);
}
