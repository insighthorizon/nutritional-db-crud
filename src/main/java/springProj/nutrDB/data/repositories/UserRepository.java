package springProj.nutrDB.data.repositories;


import org.springframework.data.repository.CrudRepository;
import springProj.nutrDB.data.entities.UserEntity;

import java.util.Optional;

/**
 * Provides CRUP operations on the persistent user entries.<br>
 * Implementation is provided by Spring Data JPA.
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    /**
     * Get a user entity with a given user email. Note: Emails are supposed to be unique.
     * A given eimail will occur either once or not at all, as defined in
     * {@link springProj.nutrDB.data.entities.UserEntity}
     * <br>
     * Spring Data JPA automatically generates implementation for this method:
     * Corresponding to the sql query "… where x.email = ?1";<br>
     * Source: <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html">Spring Doc</a>
     * @param email Found entry has to have this email.
     * @return Container either holding a found user entity or null.
     */
    Optional<UserEntity> findByEmail(String email);
}
