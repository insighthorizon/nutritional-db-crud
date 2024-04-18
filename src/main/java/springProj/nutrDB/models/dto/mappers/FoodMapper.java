package springProj.nutrDB.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import springProj.nutrDB.data.entities.FoodEntity;
import springProj.nutrDB.models.dto.FoodDTO;

/**
 * Based on this interface, Mapstruct generates code implementing it,
 * such that the implemented methods use setters and getters of source classes and target to convert between them.
 * Mappstruct is told to do that by the annotaiton @Mapper and @MappingTarget.<br>
 * (Saving us time from typing of calls to all the getters and setters.)<br>
 * The generated implementation appears in ./target/generated-sources/**
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FoodMapper {

    /**
     * Create new FoodEntity based on field values of provided FoodDTO
     * @param source provided FoodDTO
     * @return resulting FoodEntity
     */
    FoodEntity toFoodEntity(FoodDTO source);

    /**
     * Create new FoodDTO based on field values of provided FoodEntity
     * @param source provided FoodEntity
     * @return resulting FoodDTO
     */
    FoodDTO toFoodDTO(FoodEntity source);

    /**
     * Change field values of a FoodEntity instance according to field values of a FoodDTO
     * @param source FoodDTO with the source data
     * @param target FoodEntity instance whose data will get changed
     */
    void updateFoodEntity(FoodDTO source, @MappingTarget FoodEntity target);

    /**
     * Change field values of a FoodDTO instance acoording to field values of another FoodDTO
     * @param source FoodDTO with the source data
     * @param target FoodDTO being changed
     */
    void updateFoodDTO(FoodDTO source, @MappingTarget FoodDTO target);

}
