package springProj.nutrDB.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import springProj.nutrDB.data.entities.FoodEntity;
import springProj.nutrDB.models.dto.FoodDTO;

/**
 * (saving us typing of calls to all the getters and setters)
 */

// using my own custom mapper to convert between macros
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodMapper {

    FoodEntity toFoodEntity(FoodDTO source);

    FoodDTO toFoodDTO(FoodEntity source);

    void updateFoodEntity(FoodDTO source, @MappingTarget FoodEntity target);

    void updateFoodDTO(FoodDTO source, @MappingTarget FoodDTO target);

}
