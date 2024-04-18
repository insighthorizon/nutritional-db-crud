package springProj.nutrDB.models.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Defines the Data-transfer object for FoodEntity - for transfering data between model and view. It's structure reflects {@link springProj.nutrDB.data.entities.FoodEntity}.<br><br>
 * The annotations on private fields of this class specify validation of the user intput together with error messages
 * about when the validation constraint gets broken.
 * - functionality provided by Jakarta Validation API (Hibernate implementation).
 * TODO validate consistency of the entire food (total being less than 100 g, kcal matching gram values) - probably by using Cross-parameter constraint (implementing own validator). See
 * <a href="https://docs.jboss.org/hibernate/validator/8.0/reference/en-US/html_single/#section-cross-parameter-constraints">Hibernate docs</a>.
*/
public class FoodDTO {
    /**
     * no dependencies, default field values<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public FoodDTO() {}

    /**
     * ID - primary key in the database table
     */
    private long foodId;

    /**
     * name of the food
     * validation contraints: Can't be empty, can't be just blank chars, has to be under 20 characters
     */
    @NotBlank(message = "Vyplňte název potraviny.")
    @NotNull(message = "Vyplňte název potraviny.")
    @Size(max = 20, message = "Název potraviny je příliš dlouhý.")
    private String name = "";

    /**
     * caloric content of the food - kcal per 100 grams of the food
     * validation constraints: has to be between 0 and 900
     */
    @Min(value = 0, message = "Množství kalorií musí být kladné.")
    @Max(value = 900, message = "Nelze přesáhnou 900 kcal na 100 g.")
    private short kcal = 0;

    /**
     * protein content of the food - grams of protein per 100 grams of the food
     * validation constraints: 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part, min=0, max=100
     */
    @DecimalMin(value = "0.0", message = "Množství gramů musí být kladné.")
    @DecimalMax(value = "100.0", message = "Nelze přesáhnout 100 g.")
    @Digits(integer = 3, fraction = 1, message = "Bude registrována maximálně třímístná hodnota.")
    private BigDecimal protein = BigDecimal.valueOf(0, 1);

    /**
     * carbohydrate content of the food - grams of carbohydrates per 100 grams of the food
     * validation constraints: 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part, min=0, max=100
     */
    @DecimalMin(value = "0.0", message = "Množství gramů musí být kladné.")
    @DecimalMax(value = "100.0", message = "Nelze přesáhnout 100 g.")
    @Digits(integer = 3, fraction = 1, message = "Bude registrována maximálně třímístná hodnota.")
    private BigDecimal carbs = BigDecimal.valueOf(0, 1);

    /**
     * fat content of the food - grams of fat per 100 grams of the food
     * validation constraints: 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part, min=0, max=100
     */
    @DecimalMin(value = "0.0", message = "Množství gramů musí být kladné.")
    @DecimalMax(value = "100.0", message = "Nelze přesáhnout 100 g.")
    @Digits(integer = 3, fraction = 1, message = "Bude registrována maximálně třímístná hodnota.")
    private BigDecimal fats = BigDecimal.valueOf(0, 1);

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

    /**
     * Returns caloric content of the food<br>
     * validation constraints: has to be between 0 and 900
     * @return kcal per 100 grams of the food
     */
    public short getKcal() {
        return kcal;
    }

    /**
     * Sets caloric content of the food<br>
     * validation constraints: has to be between 0 and 900
     * @param kcal kcal per 100 grams of the food
     */
    public void setKcal(short kcal) {
        this.kcal = kcal;
    }

    /**
     * Returns protein content of the food<br>
     * validation constraints: 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part, min=0, max=100
     * @return grams of protein per 100 grams of the food
     */
    public BigDecimal getProtein() {
        return protein;
    }

    /**
     * Sets protein content of the food<br>
     * validation constraints: 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part, min=0, max=100
     * @param protein grams of protein per 100 grams of the food
     */
    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    /**
     * Returns carbohydrate content of the food<br>
     * validation constraints: 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part, min=0, max=100
     * @return grams of carbohydrates per 100 grams of the food
     */
    public BigDecimal getCarbs() {
        return carbs;
    }

    /**
     * Sets carbohydrate content of the food<br>
     * validation constraints: 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part, min=0, max=100
     * @param carbs grams of carbohydrates per 100 grams of the food
     */
    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }

    /**
     * Returns fat content of the food<br>
     * validation constraints: 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part, min=0, max=100
     * @return grams of fat per 100 grams of the food
     */
    public BigDecimal getFats() {
        return fats;
    }

    /**
     * Sets fat content of the food<br>
     * validation constraints: 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part, min=0, max=100
     * @param fats grams of fat per 100 grams of the food
     */
    public void setFats(BigDecimal fats) {
        this.fats = fats;
    }
}
