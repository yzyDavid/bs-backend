package bs.word;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface WordRepository extends CrudRepository<WordEntity, Long> {
    boolean existsByWord(String word);
}
