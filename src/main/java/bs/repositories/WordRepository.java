package bs.repositories;

import bs.entities.WordEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface WordRepository extends CrudRepository<WordEntity, Long> {
    boolean existsByWord(String word);

    WordEntity getById(long id);

    WordEntity getByWord(String word);
}
