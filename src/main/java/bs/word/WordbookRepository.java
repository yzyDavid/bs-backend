package bs.word;

import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
public interface WordbookRepository extends CrudRepository<WordbookEntity, Long> {
    boolean existsByWordbookName(String wordbookName);

    WordbookEntity findByWordbookName(String wordbookName);
}
