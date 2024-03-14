package springProj.nutrDB.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import springProj.nutrDB.data.entities.FoodEntity;
import springProj.nutrDB.models.dto.FoodDTO;

public interface FoodRepository extends CrudRepository<FoodEntity, Long>, PagingAndSortingRepository<FoodEntity, Long> {

    // … where x.name like ?1 (parameter bound wrapped in %)
    Page<FoodEntity> findByNameContaining(String name, Pageable pageable);
}
