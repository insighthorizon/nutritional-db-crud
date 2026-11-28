package springProj.nutrDB.data.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

/**
 * Represents one food entry in the persistence layer (one row of the database table - table structure).
 * A food entity has an id, name and nutritional content per 100 grams or 100 milliliters.
 * Nutritional content consists of four values:<br>
 * caloric content (kcal),<br>
 * protein content (grams),<br>
 * carbohydrate content (grams),<br>
 * fat content (grams).<br>
 * The nutrition basis is stored with each food. Macronutrients are measured in grams.<br><br>
 * The annotations in this class specify the PostgreSQL representation of FoodEntity. (See the private fields in source code.)<br>
 * Conversion between object and database row is based on ORM (Object-relational mapping)
 * and is provided by Jakarta Persistence API (Hibernate implementation). Both annotation and attribute data type
 * have an effect on how it is represented as a database column.
 * A table database table with corresponding structure and name is automatically created if it doesn't exist.
 */
@Entity
public class FoodEntity {

    /**
     * no dependencies, default field values<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public FoodEntity() { }

    /**
     * ID - primary key
     * represented as primarky key, bigint, not null, auto_increment in SQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long foodId;

    /**
     * name of the food
     * represented as varchar(255), not null in SQL
     */
    @Column(nullable = false)
    private String name;

    /**
     * Reference amount for the energy and macronutrient values.
     * The database default assigns existing rows to per 100 grams when this column is added.
     * {@link ColumnDefault} keeps the default expression separate from the type used in ALTER COLUMN TYPE.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    @ColumnDefault("'PER_100_GRAMS'")
    private NutritionBasis nutritionBasis = NutritionBasis.PER_100_GRAMS;

    /**
     * caloric content of the food - kcal per selected nutrition basis
     * represented as smallint(6), not null in SQL
     */
    @Column(nullable = false)
    private short kcal;

    /**
     * protein content of the food - grams per selected nutrition basis
     * represented as numeric(4,1), not null in PostgreSQL
     */
    @Column(precision = 4, scale = 1, nullable = false)
    private BigDecimal protein;

    /**
     * carbohydrate content of the food - grams per selected nutrition basis
     * represented as numeric(4,1), not null in PostgreSQL
     */
    @Column(precision = 4, scale = 1, nullable = false)
    private BigDecimal carbs;

    /**
     * fat content of the food - grams per selected nutrition basis
     * represented as numeric(4,1), not null in PostgreSQL
     */
    @Column(precision = 4, scale = 1, nullable = false)
    private BigDecimal fats;

     /**
     * Returns the food ID
     * represented as bigint(20), not null, auto_increment in SQL
     * @return unique ID (primary key) of the food entry
     */
    public long getFoodId() {
        return foodId;
    }

    /**
     * Returns the name of the food
     * represented as varchar(255), not null in SQL
     * @return food name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the food
     * represented as varchar(255), not null in SQL
     * @param name food name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** {@return the reference amount for the stored energy and macronutrient values} */
    public NutritionBasis getNutritionBasis() {
        return nutritionBasis;
    }

    /**
     * Sets the reference amount for the stored energy and macronutrient values.
     * @param nutritionBasis the reference amount to store
     */
    public void setNutritionBasis(NutritionBasis nutritionBasis) {
        this.nutritionBasis = nutritionBasis;
    }

    /**
     * Returns the caloric content of the food - kcal per selected nutrition basis
     * represented as smallint(6), not null in SQL
     * @return kcal per selected nutrition basis
     */
    public short getKcal() {
        return kcal;
    }

    /**
     * Sets the caloric content of the food - kcal per selected nutrition basis
     * represented as smallint(6), not null in SQL
     * @param kcal kcal per selected nutrition basis
     */
    public void setKcal(short kcal) {
        this.kcal = kcal;
    }

    /**
     * Returns protein content of the food in grams per selected nutrition basis.<br>
     * represented as numeric(4,1), not null in PostgreSQL
     * @return grams of protein per selected nutrition basis
     */
    public BigDecimal getProtein() {
        return protein;
    }

    /**
     * Sets protein content of the food in grams per selected nutrition basis.<br>
     * represented as numeric(4,1), not null in PostgreSQL
     * @param protein grams of protein per selected nutrition basis
     */
    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    /**
     * Returns carbohydrate content of the food in grams per selected nutrition basis.<br>
     * represented as numeric(4,1), not null in PostgreSQL
     * @return grams of carbs per selected nutrition basis
     */
    public BigDecimal getCarbs() {
        return carbs;
    }

    /**
     * Sets carbohydrate content of the food in grams per selected nutrition basis.<br>
     * represented as numeric(4,1), not null in PostgreSQL
     * @param carbs grams of carbs per selected nutrition basis
     */
    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }

    /**
     * Returns fat content of the food in grams per selected nutrition basis.<br>
     * represented as numeric(4,1), not null in PostgreSQL
     * @return grams of fat per selected nutrition basis
     */
    public BigDecimal getFats() {
        return fats;
    }

    /**
     * Sets fat content of the food in grams per selected nutrition basis.<br>
     * represented as numeric(4,1), not null in PostgreSQL
     * @param fats grams of fat per selected nutrition basis
     */
    public void setFats(BigDecimal fats) {
        this.fats = fats;
    }
}
