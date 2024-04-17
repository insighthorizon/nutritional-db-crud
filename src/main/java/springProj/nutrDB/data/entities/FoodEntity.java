package springProj.nutrDB.data.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Represents one food entry in the persistence layer (one row of the database table - table structure).
 * A food has an id, name and nutritional content per 100 grams.
 * Nutritinal content consists of four values:<br>
 * caloric content (kcal),<br>
 * protein content (grams),<br>
 * carbohydrate content (grams),<br>
 * fat content (grams).<br>
 * Those values are per 100 grams of the food.<br><br>
 * The annotations in this class specify representation of FoodEntity in the MySQL database. (See the private fields in source code.)<br>
 * Conversion between object and database row is based on ORM (Object-relational mapping)
 * and is provided by Jakarta Persistence API (Hibernate implementation). Both annotation and attribute data type
 * have an effect on how it is represented as a database column.<br>
 * Constructor: Default constructor.
 * TODO provide parameter-less constructor
 */
@Entity
public class FoodEntity {
    /**
     * ID - primary key
     * represented as bigint(20), not null, auto_increment in SQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Myslim ze to znamena auto increment v MariaDB
    private long foodId;

    /**
     * name of the food
     * represented as varchar(255), not null in SQL
     */
    @Column(nullable = false)
    private String name;

    /**
     * caloric content of the food - kcal per 100 grams of the food
     * represented as smallint(6), not null in SQL
     */
    @Column(nullable = false)
    private short kcal;

    /**
     * protein content of the food - grams of protein per 100 grams of the food
     * 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part
     * represented as decimal(3,1), not null in SQL
     * - takes up 2 bytes in the database, see <a href="https://mariadb.com/kb/en/data-type-storage-requirements/">Data Type Storage Requirements</a>
     */
    @Column(precision = 3, scale = 1, nullable = false)
    private BigDecimal protein;

    /**
     * carbohydrate content of the food - grams of carbohydrates per 100 grams of the food
     * 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part
     * represented as decimal(3,1), not null in SQL
     * - takes up 2 bytes in the database, see <a href="https://mariadb.com/kb/en/data-type-storage-requirements/">Data Type Storage Requirements</a>
     */
    @Column(precision = 3, scale = 1, nullable = false)
    private BigDecimal carbs;

    /**
     * fat content of the food - grams of fat per 100 grams of the food
     * 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part
     * represented as decimal(3,1), not null in SQL
     * - takes up 2 bytes in the database, see <a href="https://mariadb.com/kb/en/data-type-storage-requirements/">Data Type Storage Requirements</a>
     */
    @Column(precision = 3, scale = 1, nullable = false)
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

    /**
     * Returns the caloric content of the food - kcal per 100 grams of the food
     * represented as smallint(6), not null in SQL
     * @return kcal per 100 grams of the food
     */
    public short getKcal() {
        return kcal;
    }

    /**
     * Sets the caloric content of the food - kcal per 100 grams of the food
     * represented as smallint(6), not null in SQL
     * @param kcal kcal per 100 grams of the food
     */
    public void setKcal(short kcal) {
        this.kcal = kcal;
    }

    /**
     * Returns protein content of the food - grams of protein per 100 grams of the food.<br>
     * 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part
     * represented as decimal(3,1), not null in SQL
     * - takes up 2 bytes in the database, see <a href="https://mariadb.com/kb/en/data-type-storage-requirements/">Data Type Storage Requirements</a>
     * @return grams of protein per 100 grams of the food
     */
    public BigDecimal getProtein() {
        return protein;
    }

    /**
     * Returns protein content of the food - grams of protein per 100 grams of the food.<br>
     * 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part
     * represented as decimal(3,1), not null in SQL
     * - takes up 2 bytes in the database, see <a href="https://mariadb.com/kb/en/data-type-storage-requirements/">Data Type Storage Requirements</a>
     * @param protein grams of protein per 100 grams of the food
     */
    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    /**
     * Returns carbohydrate content of the food - grams of carbohydrates per 100 grams of the food.<br>
     * 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part
     * represented as decimal(3,1), not null in SQL
     * - takes up 2 bytes in the database, see <a href="https://mariadb.com/kb/en/data-type-storage-requirements/">Data Type Storage Requirements</a>
     * @return grams of carbs per 100 grams of the food
     */
    public BigDecimal getCarbs() {
        return carbs;
    }

    /**
     * Sets carbohydrate content of the food - grams of carbohydrates per 100 grams of the food.<br>
     * 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part
     * represented as decimal(3,1), not null in SQL
     * - takes up 2 bytes in the database, see <a href="https://mariadb.com/kb/en/data-type-storage-requirements/">Data Type Storage Requirements</a>
     * @param carbs grams of carbs per 100 grams of the food
     */
    public void setCarbs(BigDecimal carbs) {
        this.carbs = carbs;
    }

    /**
     * Returns fat content of the food - grams of fat per 100 grams of the food.<br>
     * 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part
     * represented as decimal(3,1), not null in SQL
     * - takes up 2 bytes in the database, see <a href="https://mariadb.com/kb/en/data-type-storage-requirements/">Data Type Storage Requirements</a>
     * @return grams of fat per 100 grams of the food
     */
    public BigDecimal getFats() {
        return fats;
    }

    /**
     * Sets fat content of the food - grams of fat per 100 grams of the food.<br>
     * 3-digit decimal number, 2 digits for integer part, 1 digit for the decimal part
     * represented as decimal(3,1), not null in SQL
     * - takes up 2 bytes in the database, see <a href="https://mariadb.com/kb/en/data-type-storage-requirements/">Data Type Storage Requirements</a>
     * @param fats grams of fat per 100 grams of the food
     */
    public void setFats(BigDecimal fats) {
        this.fats = fats;
    }
}
