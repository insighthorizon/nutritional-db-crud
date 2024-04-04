package springProj.nutrDB.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springProj.nutrDB.data.entities.FoodEntity;
import springProj.nutrDB.data.repositories.FoodRepository;
import springProj.nutrDB.models.dto.FoodDTO;
import springProj.nutrDB.models.dto.mappers.FoodMapper;
import springProj.nutrDB.models.exceptions.FoodNotFoundException;


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
    public Page<FoodDTO> getPage(int pageNumber, int pageSize, String searchedName, String sortAttribute) {
        // check that the page we are asking for makes sense
        if (pageNumber < 1) pageNumber = 1;

        // handling the mode of sorting
        Sort sort;
        if (sortAttribute == null)
            sortAttribute = "";

        switch (sortAttribute) { // check that the sorting mode we are asking for is alright
            // the cases correspond to the attributes we can sort by
            case "foodId", "name", "kcal" -> sort = Sort.by(Sort.Direction.ASC, sortAttribute);
            case "protein" -> sort = Sort.by(Sort.Direction.DESC, sortAttribute);
            default -> {
                sort = Sort.by(Sort.Direction.ASC, "foodId");}
        }

        // getting the page
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<FoodEntity> page = foodRepository.findByNameContaining(searchedName, pageRequest);
        int totalPages = page.getTotalPages();
        // try to get nonempty page unless the number of pages is 0
        while ((pageNumber > totalPages) && (totalPages != 0)) {
            pageNumber = totalPages;
            pageRequest = PageRequest.of(pageNumber - 1, pageSize, sort);
            page = foodRepository.findByNameContaining(searchedName, pageRequest);
            totalPages = page.getTotalPages();
        }

        return page.map(x -> foodMapper.toFoodDTO(x)); // mapping the entity page to dto page
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
