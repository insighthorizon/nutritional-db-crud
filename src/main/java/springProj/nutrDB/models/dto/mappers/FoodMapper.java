package springProj.nutrDB.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import springProj.nutrDB.data.entities.FoodEntity;
import springProj.nutrDB.models.dto.FoodDTO;


// using my own custom mapper to convert between macros
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses=MacronutrientMapper.class)
public interface FoodMapper {

    FoodEntity toFoodEntity(FoodDTO source);

    FoodDTO toFoodDTO(FoodEntity source);

    void updateFoodEntity(FoodDTO source, @MappingTarget FoodEntity target);

    void updateFoodDTO(FoodDTO source, @MappingTarget FoodDTO target);

}
