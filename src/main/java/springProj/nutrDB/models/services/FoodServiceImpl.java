package springProj.nutrDB.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import springProj.nutrDB.data.entities.FoodEntity;
import springProj.nutrDB.data.repositories.FoodRepository;
import springProj.nutrDB.models.dto.FoodDTO;
import springProj.nutrDB.models.dto.mappers.FoodMapper;
import springProj.nutrDB.models.exceptions.FoodNotFoundException;
import springProj.nutrDB.models.exceptions.GramValueException;
import springProj.nutrDB.models.exceptions.KcalMismatchException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodMapper foodMapper;

    @Override
    public void create(FoodDTO food){
        FoodEntity newFood = foodMapper.toFoodEntity(food);
        foodRepository.save(newFood);

    }

    @Override
    public List<FoodDTO> getAll() {
        // https://www.baeldung.com/java-iterable-to-stream
        return StreamSupport
                .stream(foodRepository.findAll().spliterator(), false)
                .map(x -> foodMapper.toFoodDTO(x))
                .toList();
    }

    @Override
    public Page<FoodDTO> getPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        return foodRepository
                .findAll(pageRequest)
                .map(x -> foodMapper.toFoodDTO(x));
    }

    @Override
    public Page<FoodDTO> getNamesearchPage(int pageNumber, int pageSize, String searchedName) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        return foodRepository
                .findByNameContaining(searchedName, pageRequest)
                .map(x -> foodMapper.toFoodDTO(x));
    }

    private FoodEntity getFoodOrThrow(long foodId) {
        return foodRepository
                .findById(foodId)
                .orElseThrow(() -> new FoodNotFoundException());
    }

    @Override
    public FoodDTO getById(long foodId) {
        FoodEntity fetchedEntity = getFoodOrThrow(foodId);
        return foodMapper.toFoodDTO(fetchedEntity);
    }

    @Override
    public void edit(FoodDTO food) {
        FoodEntity fetchedEntity = getFoodOrThrow(food.getFoodId());
        foodMapper.updateFoodEntity(food, fetchedEntity);
        foodRepository.save(fetchedEntity);
    }

    @Override
    public void remove(long foodId) {
        FoodEntity fetchedEntity = getFoodOrThrow(foodId);
        foodRepository.delete(fetchedEntity);
    }


}
