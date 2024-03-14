package springProj.nutrDB.data.entities;

import jakarta.persistence.*;

@Entity
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Myslim ze to znamena auto increment v MariaDB
    private long foodId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer kcal;

    @Column(nullable = false)
    private Integer protein;

    @Column(nullable = false)
    private Integer carbs;

    @Column(nullable = false)
    private Integer fats;

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

    public Integer getKcal() {
        return kcal;
    }

    public void setKcal(Integer kcal) {
        this.kcal = kcal;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getCarbs() {
        return carbs;
    }

    public void setCarbs(Integer carbs) {
        this.carbs = carbs;
    }

    public Integer getFats() {
        return fats;
    }

    public void setFats(Integer fats) {
        this.fats = fats;
    }
}
