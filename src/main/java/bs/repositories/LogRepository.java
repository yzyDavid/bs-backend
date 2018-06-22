package bs.repositories;

import bs.entities.LogEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;

/**
 * @author yzy
 */
public interface LogRepository extends CrudRepository<LogEntity, Long> {
    public boolean existsByEmail(String email);

    public Collection<LogEntity> getAllByEmail(String email);

    public boolean existsByEmailAndDate(String email, Date date);

    public LogEntity getByEmailAndDate(String email, Date date);
}
