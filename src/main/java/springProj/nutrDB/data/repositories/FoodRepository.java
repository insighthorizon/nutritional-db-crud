package springProj.nutrDB.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import springProj.nutrDB.data.entities.FoodEntity;


/**
 * Provides CRUP operations with paging and sorting on the persistent food entries.<br>
 * Implementation is provided by Spring Data JPA.
 */
public interface FoodRepository extends CrudRepository<FoodEntity, Long>, PagingAndSortingRepository<FoodEntity, Long> {
    /**
     * Get a page of food entries containing a given substring in their name.
     * <br>
     * Spring Data JPA automatically generates implementation for this method:
     * Corresponding to the sql query "… where x.name like ?1 (parameter bound wrapped in %)";
     * Source: <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html">Spring Doc</a><br><br>
     * This performs the LIMIT OFFSET pagination (drifting possible, so the user may miss entries when paging);<br>
     * <a href="https://www.youtube.com/watch?v=zwDIN04lIpc">Explanation video</a><br><br>
     * The alternative (pivot based pagination) is more complicated, plus instead of drifting there is no way
     * to directly address a page (no skipping between pages).
     * @param searchedName Only entries containing this as substring in their name will be contained in the result.
     * @param pageRequest Specifies pagination and sorting of the result - like page size, page number, if sorting is used,
     *                 what attribute to sort by ...
     * @return
     */
    Page<FoodEntity> findByNameContaining(String searchedName, Pageable pageRequest);
}
