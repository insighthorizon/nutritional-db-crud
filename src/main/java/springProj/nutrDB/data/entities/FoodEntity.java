package springProj.nutrDB.data.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Myslim ze to znamena auto increment v MariaDB
    private long foodId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private short kcal;

    // ukladame 3-mistne cislo ktere ma 2 mista pred a 1 misto za desetinnou carkou
    @Column(precision = 3, scale = 1, nullable = false)
    private BigDecimal protein;

    @Column(precision = 3, scale = 1, nullable = false)
    private BigDecimal carbs;

    @Column(precision = 3, scale = 1, nullable = false)
    private BigDecimal fats;

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
