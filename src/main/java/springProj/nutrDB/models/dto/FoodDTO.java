package springProj.nutrDB.models.dto;

import jakarta.validation.constraints.*;
import springProj.nutrDB.data.entities.NutritionBasis;

import java.math.BigDecimal;

/**
 * Defines the Data-transfer object for FoodEntity - for transfering data between model and view. Its structure reflects {@link springProj.nutrDB.data.entities.FoodEntity}.<br><br>
 * The annotations on private fields of this class specify validation constraints of the user intput together with error messages
 * about when the validation constraint gets broken. WHEN validation is applied is defined by another annotation in a controller.<br>
 * - functionality provided by Jakarta Validation API (Hibernate implementation).
 * TODO validate consistency of the entire food (total being less than 100 g, kcal matching gram values) - probably by using Cross-parameter constraint (implementing own validator). See
 * <a href="https://docs.jboss.org/hibernate/validator/8.0/reference/en-US/html_single/#section-cross-parameter-constraints">Hibernate docs</a>.
*/
public class FoodDTO {
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
     * validation contraints: Can't be empty, can't be just blank chars, has to be under 20 characters
     */
    @NotBlank(message = "Food name is mandatory.")
    @NotNull(message = "Food name is mandatory.")
    @Size(max = 40, message = "Food name is too long.")
    private String name = "";

    @NotNull(message = "Choose whether the values are per 100 g or per 100 ml.")
    private NutritionBasis nutritionBasis = NutritionBasis.PER_100_GRAMS;

    /**
     * caloric content of the food - kcal per selected nutrition basis
     * validation constraints: has to be between 0 and 900
     */
    @Min(value = 0, message = "Kcal value must be positive.")
    @Max(value = 900, message = "Impossibly big kcal value.")
    private short kcal = 0;

    /**
     * protein content of the food - grams per selected nutrition basis
     * validation constraints: at most 3 integer digits and 1 fractional digit; per-100-g values cannot exceed 100 g
     */
    @DecimalMin(value = "0.0", message = "Gram value must be positive.")
    @Digits(integer = 3, fraction = 1, message = "Maximum three digits can be saved.")
    private BigDecimal protein = BigDecimal.valueOf(0, 1);

    /**
     * carbohydrate content of the food - grams per selected nutrition basis
     * validation constraints: at most 3 integer digits and 1 fractional digit; per-100-g values cannot exceed 100 g
     */
    @DecimalMin(value = "0.0", message = "Gram value must be positive.")
    @Digits(integer = 3, fraction = 1, message = "Maximum three digits can be saved.")
    private BigDecimal carbs = BigDecimal.valueOf(0, 1);

    /**
     * fat content of the food - grams per selected nutrition basis
     * validation constraints: at most 3 integer digits and 1 fractional digit; per-100-g values cannot exceed 100 g
     */
    @DecimalMin(value = "0.0", message = "Gram value must be positive.")
    @Digits(integer = 3, fraction = 1, message = "Maximum three digits can be saved.")
    private BigDecimal fats = BigDecimal.valueOf(0, 1);

    /**
     * A 100 g serving cannot contain more than 100 g of any macronutrient.
     * Per-100-ml values may exceed 100 g for dense liquids.
     * Spring calls this Bean Validation check after binding form data to this DTO
     * and before entering the create or edit controller method, because those
     * methods receive the DTO with @Valid.
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

    public NutritionBasis getNutritionBasis() {
        return nutritionBasis;
    }

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
