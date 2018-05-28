package bs.repositories;

import bs.entities.WordbookEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * @author yzy
 */
@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface WordbookRepository extends CrudRepository<WordbookEntity, Long> {
    boolean existsByWordbookName(String wordbookName);

    WordbookEntity findByWordbookName(String wordbookName);

    Collection<WordbookEntity> findAll();
}
