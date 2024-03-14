package springProj.nutrDB.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import springProj.nutrDB.data.entities.FoodEntity;
import springProj.nutrDB.models.dto.FoodDTO;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodMapper {

    abstract FoodEntity toFoodEntity(FoodDTO source);

    abstract FoodDTO toFoodDTO(FoodEntity source);

    abstract void updateFoodEntity(FoodDTO source, @MappingTarget FoodEntity target);

    abstract void updateFoodDTO(FoodDTO source, @MappingTarget FoodDTO target);


}
