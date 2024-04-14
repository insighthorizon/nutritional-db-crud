package springProj.nutrDB.models.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;


// TODO validate consistency of the entire food (total being <= 100 g, kcal matching gram values)
// - using Cross-parameter constraint (implementing own validator)
// https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html/validator-gettingstarted.html#validator-gettingstarted
// https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html/validator-customconstraints.html#section-custom-property-paths
// https://docs.jboss.org/hibernate/validator/8.0/reference/en-US/html_single/#section-cross-parameter-constraints

public class FoodDTO {

    /**
     * TODO create parameterless constructor
     */

    private long foodId;

    @NotBlank(message = "Vyplňte název potraviny.")
    @NotNull(message = "Vyplňte název potraviny.")
    @Size(max = 20, message = "Název potraviny je příliš dlouhý.")
    private String name = "";

    @Min(value = 0, message = "Množství kalorií musí být kladné.")
    @Max(value = 900, message = "Nelze přesáhnou 900 kcal na 100 g.")
    private short kcal = 0;

    // BigDecimal needs to be used for proper validation
    // pracujeme s desetinnym cislem: 2 cislice pres desetinnou ., 1 cislice za
    @DecimalMin(value = "0.0", message = "Množství gramů musí být kladné.")
    @DecimalMax(value = "100.0", message = "Nelze přesáhnout 100 g.")
    @Digits(integer = 3, fraction = 1, message = "Bude registrována maximálně třímístná hodnota.")
    private BigDecimal protein = BigDecimal.valueOf(0, 1);

    @DecimalMin(value = "0.0", message = "Množství gramů musí být kladné.")
    @DecimalMax(value = "100.0", message = "Nelze přesáhnout 100 g.")
    @Digits(integer = 3, fraction = 1, message = "Bude registrována maximálně třímístná hodnota.")
    private BigDecimal carbs = BigDecimal.valueOf(0, 1);

    @DecimalMin(value = "0.0", message = "Množství gramů musí být kladné.")
    @DecimalMax(value = "100.0", message = "Nelze přesáhnout 100 g.")
    @Digits(integer = 3, fraction = 1, message = "Bude registrována maximálně třímístná hodnota.")
    private BigDecimal fats = BigDecimal.valueOf(0, 1);

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getKcal() {
        return kcal;
    }

    public void setKcal(short kcal) {
        this.kcal = kcal;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getCarbs() {
        return carbs;
    }

    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }

    public BigDecimal getFats() {
        return fats;
    }

    public void setFats(BigDecimal fats) {
        this.fats = fats;
    }
}
