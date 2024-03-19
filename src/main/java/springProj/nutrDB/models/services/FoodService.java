package springProj.nutrDB.models.services;

import org.springframework.data.domain.Page;
import springProj.nutrDB.models.dto.FoodDTO;
import springProj.nutrDB.models.exceptions.GramValueException;
import springProj.nutrDB.models.exceptions.KcalMismatchException;

import java.util.List;

public interface FoodService {

    void create (FoodDTO food);

    List<FoodDTO> getAll();

    Page<FoodDTO> getPage(int pageNumber, int pageSize);

    Page<FoodDTO> getNamesearchPage(int pageNumber, int pageSize, String searchedName);

    FoodDTO getById(long foodId);

    void edit(FoodDTO food);

    void remove(long foodId);

}
