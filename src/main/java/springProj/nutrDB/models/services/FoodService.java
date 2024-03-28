package springProj.nutrDB.models.services;

import org.springframework.data.domain.Page;
import springProj.nutrDB.models.dto.FoodDTO;

import java.util.List;

public interface FoodService {

    void create (FoodDTO food);

    List<FoodDTO> getAll();

    // TODO implement choice of sorting
    // We will do sorting only in ascending order, because user can jump to the end anyway
    // uses limit/offset pagination (drifting possible) - that's the daful from PagingAndSortingRepository
    Page<FoodDTO> getPage(int pageNumber, int pageSize, String searchedName, String sortMode);

    FoodDTO getById(long foodId);

    void edit(FoodDTO food);

    void remove(long foodId);

}
