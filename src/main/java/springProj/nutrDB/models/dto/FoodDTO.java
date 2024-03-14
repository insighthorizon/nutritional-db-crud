package springProj.nutrDB.models.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FoodDTO {

    private long foodId;

    @NotBlank(message = "Vyplňte název potraviny.")
    @NotNull(message = "Vyplňte název potraviny.")
    @Size(max = 20, message = "Název potraviny je příliš dlouhý.")
    private String name = "";

    @Min(value = 0, message = "Množství kalorií musí být kladné.")
    @Max(value = 900, message = "Nelze přesáhnou 900 kcal na 100 g.")
    private int kcal = 0;

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

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    // These setters are not default generated
    // they perform rounding!
    // TODO that's something to UNIT TEST
    // this problem could be avoided by not using floats in the database - even short integer would be enough
       // that would require more complicated mapper - can be done with MapStruct though https://mapstruct.org/documentation/stable/reference/html/#adding-custom-methods
    public void setProtein(BigDecimal protein) {
        this.protein = protein.setScale(1, RoundingMode.HALF_UP);
    }

    public BigDecimal getCarbs() {
        return carbs;
    }

    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs.setScale(1, RoundingMode.HALF_UP);
    }

    public BigDecimal getFats() {
        return fats;
    }

    public void setFats(BigDecimal fats) {
        this.fats = fats.setScale(1, RoundingMode.HALF_UP);
    }
}
