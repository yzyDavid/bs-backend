package bs.repositories;

import bs.entities.SessionEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface SqlSessionRepository extends CrudRepository<SessionEntity, Long> {
    boolean existsByEmail(String email);

    SessionEntity findByEmail(String email);

    boolean existsByToken(String token);

    SessionEntity findByToken(String token);
}
