package bs.repositories;

import bs.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yzy
 */
@SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * @param email
     * @return boolean
     * check exists of a UID
     */
    boolean existsByEmail(String email);

    UserEntity findByEmail(String email);

    boolean existsByName(String name);

    UserEntity findByName(String name);
}
