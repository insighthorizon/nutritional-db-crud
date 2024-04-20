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

/**
 * Implementation of {@link springProj.nutrDB.models.services.FoodService}
 */
@Service
public class FoodServiceImpl implements FoodService {

    /**
     * Constructor used by Spring IoC container to instantiate this class and
     * to inject dependencies (via constructor parameters).
     * This class has only one constructor, so no @Autowired annotation needed.
     * @param foodRepository instance provided by dependency injection
     * @param foodMapper instance provided by dependency injection
     */
    public FoodServiceImpl(FoodRepository foodRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
    }

    /**
     * Everything in this service reflects into the Model (persistance layer) through this repository.
     */
    private FoodRepository foodRepository;

    /**
     * For conversion between DTO and entity without the need to type all the getters/setters.
     */
    private FoodMapper foodMapper;

    @Override
    public void create(FoodDTO food){
        FoodEntity newFood = foodMapper.toFoodEntity(food);
        foodRepository.save(newFood);

    }

    @Override
    public List<FoodDTO> getAll() {
        return StreamSupport // first conversion to stream: https://www.baeldung.com/java-iterable-to-stream
                .stream(foodRepository.findAll().spliterator(), false)
                .map(x -> foodMapper.toFoodDTO(x))
                .toList();
    }


    /**
     * {@inheritDoc}
     * <br><br>
     * The sorting order (ascending/descending) is fixed based on the attributed user is sorting by.
     * Once we know by which attribute we are sorting, it is obvious what the order is from the output.
     * So there is no information about that given.
     */
    @Override
    public Page<FoodDTO> getPage(int pageNumber, int pageSize, String searchedName, String sortAttribute) {
        // check that the page we are asking for makes sense
        if (pageNumber < 1) pageNumber = 1;

        // resolve the sorting mode
        Sort sort;
        if (sortAttribute == null)
            sortAttribute = "";

        switch (sortAttribute) { // check that the sorting mode we are asking for is alright
            // the cases correspond to the attributes we can sort by
            case "foodId", "name", "kcal" -> sort = Sort.by(Sort.Direction.ASC, sortAttribute);
            case "protein", "fats" -> sort = Sort.by(Sort.Direction.DESC, sortAttribute);
            default -> sort = Sort.by(Sort.Direction.ASC, "foodId");
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

    /**
     * Private helper method for finding food entry by id and handling the case when the entry isn't found.
     * This would happen for example if multiple users access the database and the original entry was deleted before the request for this could be finished.
     * <br>
     * If the optional container returned by foodRepository.findById() is "empty", then this method throws appropriate exception.
     * The exception is supposed to have its own exception handler in the controller.
     * @param foodId ID of the food entry being requested
     * @return The food entity found.
     */
    private FoodEntity getFoodOrThrow(long foodId) throws FoodNotFoundException{
        return foodRepository
                .findById(foodId)
                .orElseThrow(() -> new FoodNotFoundException());
    }

    @Override
    public FoodDTO getById(long foodId) throws FoodNotFoundException {
        FoodEntity fetchedEntity = getFoodOrThrow(foodId);
        return foodMapper.toFoodDTO(fetchedEntity);
    }

    @Override
    public void edit(FoodDTO food) throws FoodNotFoundException{
        FoodEntity fetchedEntity = getFoodOrThrow(food.getFoodId());
        foodMapper.updateFoodEntity(food, fetchedEntity);
        foodRepository.save(fetchedEntity);
    }

    @Override
    public void remove(long foodId) throws FoodNotFoundException{
        FoodEntity fetchedEntity = getFoodOrThrow(foodId);
        foodRepository.delete(fetchedEntity);
    }


}
