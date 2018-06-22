package bs.repositories;

import bs.entities.LogEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface LogRepository extends CrudRepository<LogEntity, Long> {
}
