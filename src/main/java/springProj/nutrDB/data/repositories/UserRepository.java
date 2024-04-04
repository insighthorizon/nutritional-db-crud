package springProj.nutrDB.data.repositories;


import org.springframework.data.repository.CrudRepository;
import springProj.nutrDB.data.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email); // spring data jpa automaticky implementuje tuto metodu - bude generovat prislusny dotaz na mariadb
}
