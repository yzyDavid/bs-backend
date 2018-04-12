package bs.repositories;

import bs.entities.SessionEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface SqlSessionRepository extends CrudRepository<SessionEntity, Long> {
    boolean existsByEmail(String uid);

    SessionEntity findByEmail(String uid);

    boolean existsByToken(String token);

    SessionEntity findByToken(String token);
}
