package springProj.nutrDB.models.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import springProj.nutrDB.data.entities.NutritionBasis;
import springProj.nutrDB.models.validation.ValidFoodNutrition;

import java.math.BigDecimal;

/**
 * Transfers food data between the model and view. Its structure reflects
 * {@link springProj.nutrDB.data.entities.FoodEntity}.
 * Jakarta Validation constraints on individual properties check their values.
 * The class-level constraint checks how several values fit together when saving a food.
 */
@ValidFoodNutrition(groups = FoodDTO.NutritionChecks.class)
public class FoodDTO {
    /**
     * The create and edit controller handlers request this group sequence. Run {@link Default} first,
     * which includes constraints declared without an explicit group. Run {@link NutritionChecks}
     * only if the first group passes.
     *
     * @see <a href="https://jakarta.ee/specifications/bean-validation/3.1/apidocs/jakarta/validation/groups/package-summary.html">Jakarta Validation groups and the Default group</a>
     * @see <a href="https://docs.hibernate.org/validator/9.1/reference/en-US/html_single/#section-defining-group-sequences">Hibernate Validator group sequence example</a>
     * ...
     * Our validation  sequence:
     * 1. default group (all those @Min, @Max, @NotBlank, @Size) constraints
     * 2. NutritionChecks group.
     */
    @GroupSequence({Default.class, NutritionChecks.class})
    public interface Save {}

    /** Marker interface naming the group for the nutrition constraints applied during a save.
     * Certain constraints, at field level or even class level are assigned to the group:
     * {@code @ValidFoodNutrition} on the class
     * {@code @NotNull} on protein
     * {@code @NotNull} on carbs
     * {@code @NotNull} on fats */
    public interface NutritionChecks {}

    /**
     * no dependencies, default field values<br>
     */
    public FoodDTO() {}

    /**
     * ID (primary key in the database table)
     */
    private long foodId;

    /**
     * name of the food
     * validation contraints: Can't be empty, can't be just blank chars, has to be under 40 characters
     */
    @NotBlank(message = "Food name is mandatory.")
    @Size(max = 40, message = "Food name is too long.")
    private String name = "";

    @NotNull(message = "Choose whether the values are per 100 g or per 100 ml.")
    private NutritionBasis nutritionBasis = NutritionBasis.PER_100_GRAMS;

    /**
     * caloric content of the food - kcal per selected nutrition basis
     * validation constraints: has to be between 0 and 900
     */
    @Min(value = 0, message = "Kcal value can't be negative.")
    @Max(value = 900, message = "Impossibly big kcal value.")
    private short kcal = 0;

    /**
     * protein content of the food - grams per selected nutrition basis
     * validation constraints: at most 3 integer digits and 1 fractional digit; per-100-g values cannot exceed 100 g
     */
    @DecimalMin(value = "0.0", message = "Gram value can't be negative.")
    @Digits(integer = 3, fraction = 1, message = "Maximum three digits can be saved.")
    @NotNull(groups = NutritionChecks.class, message = "Enter the amount of protein in grams.")
    private BigDecimal protein = BigDecimal.valueOf(0, 1);

    /**
     * carbohydrate content of the food - grams per selected nutrition basis
     * validation constraints: at most 3 integer digits and 1 fractional digit; per-100-g values cannot exceed 100 g
     */
    @DecimalMin(value = "0.0", message = "Gram value can't be negative.")
    @Digits(integer = 3, fraction = 1, message = "Maximum three digits can be saved.")
    @NotNull(groups = NutritionChecks.class, message = "Enter the amount of carbohydrates in grams.")
    private BigDecimal carbs = BigDecimal.valueOf(0, 1);

    /**
     * fat content of the food - grams per selected nutrition basis
     * validation constraints: at most 3 integer digits and 1 fractional digit; per-100-g values cannot exceed 100 g
     */
    @DecimalMin(value = "0.0", message = "Gram value can't be negative.")
    @Digits(integer = 3, fraction = 1, message = "Maximum three digits can be saved.")
    @NotNull(groups = NutritionChecks.class, message = "Enter the amount of fat in grams.")
    private BigDecimal fats = BigDecimal.valueOf(0, 1);

    /**
     * A 100 g serving cannot contain more than 100 g of any macronutrient.
     * Per-100-ml values may exceed 100 g for dense liquids.
     * This property constraint belongs to the Default group, which both create and edit validate.
     * @return {@code false} if a supplied macronutrient exceeds 100 g on the per 100 g basis, {@code true} otherwise
     */
    @AssertTrue(message = "A per-100-g macronutrient value cannot exceed 100 g.")
    public boolean isNutrientValuesValidForBasis() {
        if (nutritionBasis != NutritionBasis.PER_100_GRAMS) {
            return true;
        }
        return isAtMost100(protein) && isAtMost100(carbs) && isAtMost100(fats);
    }

    private boolean isAtMost100(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.valueOf(100)) <= 0;
    }

    /**
     * ID - primary key in the database table
     * @return id of the food entry
     */
    public long getFoodId() {
        return foodId;
    }

    /**
     * ID - primary key in the database table
     * @param foodId id of the food entry
     */
    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    /**
     * validation contraints: Can't be empty, can't be just blank chars, has to be under 20 characters
     * @return name of the food
     */
    public String getName() {
        return name;
    }

    /**
     * validation contraints: Can't be empty, can't be just blank chars, has to be under 20 characters
     * @param name name of the food
     */
    public void setName(String name) {
        this.name = name;
    }

    /** {@return the reference amount for the submitted energy and macronutrient values} */
    public NutritionBasis getNutritionBasis() {
        return nutritionBasis;
    }

    /**
     * Sets the reference amount for the submitted energy and macronutrient values.
     * @param nutritionBasis the selected reference amount
     */
    public void setNutritionBasis(NutritionBasis nutritionBasis) {
        this.nutritionBasis = nutritionBasis;
    }

    /**
     * Returns caloric content of the food<br>
     * validation constraints: has to be between 0 and 900
     * @return kcal per selected nutrition basis
     */
    public short getKcal() {
        return kcal;
    }

    /**
     * Sets caloric content of the food<br>
     * validation constraints: has to be between 0 and 900
     * @param kcal kcal per selected nutrition basis
     */
    public void setKcal(short kcal) {
        this.kcal = kcal;
    }

    /**
     * Returns protein content of the food<br>
     * validation constraints: non-negative, at most 3 integer digits and 1 fractional digit; per-100-g values are capped at 100 g
     * @return grams of protein per selected nutrition basis
     */
    public BigDecimal getProtein() {
        return protein;
    }

    /**
     * Sets protein content of the food<br>
     * validation constraints: non-negative, at most 3 integer digits and 1 fractional digit; per-100-g values are capped at 100 g
     * @param protein grams of protein per selected nutrition basis
     */
    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    /**
     * Returns carbohydrate content of the food<br>
     * validation constraints: non-negative, at most 3 integer digits and 1 fractional digit; per-100-g values are capped at 100 g
     * @return grams of carbohydrates per selected nutrition basis
     */
    public BigDecimal getCarbs() {
        return carbs;
    }

    /**
     * Sets carbohydrate content of the food<br>
     * validation constraints: non-negative, at most 3 integer digits and 1 fractional digit; per-100-g values are capped at 100 g
     * @param carbs grams of carbohydrates per selected nutrition basis
     */
    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }

    /**
     * Returns fat content of the food<br>
     * validation constraints: non-negative, at most 3 integer digits and 1 fractional digit; per-100-g values are capped at 100 g
     * @return grams of fat per selected nutrition basis
     */
    public BigDecimal getFats() {
        return fats;
    }

    /**
     * Sets fat content of the food<br>
     * validation constraints: non-negative, at most 3 integer digits and 1 fractional digit; per-100-g values are capped at 100 g
     * @param fats grams of fat per selected nutrition basis
     */
    public void setFats(BigDecimal fats) {
        this.fats = fats;
    }
}
