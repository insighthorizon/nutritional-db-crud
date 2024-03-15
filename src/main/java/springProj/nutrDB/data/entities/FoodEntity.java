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
    private short kcal;

    //@Column(precision = 3, scale = 1, nullable = false)
    // private BigDecimal protein;
    @Column(nullable = false)
    private short protein;

    @Column(nullable = false)
    private short carbs;

    @Column(nullable = false)
    private short fats;

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

    public short getProtein() {
        return protein;
    }

    public void setProtein(short protein) {
        this.protein = protein;
    }

    public short getCarbs() {
        return carbs;
    }

    public void setCarbs(short carbs) {
        this.carbs = carbs;
    }

    public short getFats() {
        return fats;
    }

    public void setFats(short fats) {
        this.fats = fats;
    }
}
