package springProj.nutrDB.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springProj.nutrDB.data.entities.FoodEntity;
import springProj.nutrDB.data.entities.NutritionBasis;
import springProj.nutrDB.data.repositories.FoodRepository;

import java.math.BigDecimal;
import java.util.List;


/**
 * Initializes the FoodEntity table with demo data
 */
@Component
public class FoodDataInitializer implements CommandLineRunner {

    private final FoodRepository foodRepository;

    /**
     * Creates the initializer that seeds an empty food table.
     * @param foodRepository repository used to check for and insert food entries
     */
    public FoodDataInitializer(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (foodRepository.count() > 0) // Fill the foodRepository iff it is empty
        {
            return;
        }

        foodRepository.saveAll(List.of(
                food("Apple", 52, "0.3", "13.8", "0.2"),
                food("Banana", 89, "1.1", "22.8", "0.3"),
                food("Orange", 47, "0.9", "11.8", "0.1"),
                food("Pear", 57, "0.4", "15.2", "0.1"),
                food("Peach", 39, "0.9", "9.5", "0.3"),
                food("Plum", 46, "0.7", "11.4", "0.3"),
                food("Strawberries", 32, "0.7", "7.7", "0.3"),
                food("Blueberries", 57, "0.7", "14.5", "0.3"),
                food("Raspberries", 52, "1.2", "11.9", "0.7"),
                food("Grapes", 69, "0.7", "18.1", "0.2"),
                food("Watermelon", 30, "0.6", "7.6", "0.2"),
                food("Pineapple", 50, "0.5", "13.1", "0.1"),
                food("Mango", 60, "0.8", "15.0", "0.4"),
                food("Kiwi", 61, "1.1", "14.7", "0.5"),
                food("Avocado", 160, "2.0", "8.5", "14.7"),
                food("Cherries", 63, "1.1", "16.0", "0.2"),
                food("Apricot", 48, "1.4", "11.1", "0.4"),
                food("Pomegranate", 83, "1.7", "18.7", "1.2"),
                food("Coconut flesh", 354, "3.3", "15.2", "33.5"),
                food("Carrot", 41, "0.9", "9.6", "0.2"),
                food("Broccoli", 34, "2.8", "6.6", "0.4"),
                food("Cauliflower", 25, "1.9", "5.0", "0.3"),
                food("Spinach", 23, "2.9", "3.6", "0.4"),
                food("Kale", 49, "4.3", "8.8", "0.9"),
                food("Tomato", 18, "0.9", "3.9", "0.2"),
                food("Cucumber", 15, "0.7", "3.6", "0.1"),
                food("Red bell pepper", 31, "1.0", "6.0", "0.3"),
                food("Green peas", 81, "5.4", "14.5", "0.4"),
                food("Sweet corn", 86, "3.3", "19.0", "1.4"),
                food("Onion", 40, "1.1", "9.3", "0.1"),
                food("Garlic", 149, "6.4", "33.1", "0.5"),
                food("Potato", 77, "2.0", "17.5", "0.1"),
                food("Sweet potato", 86, "1.6", "20.1", "0.1"),
                food("Mushrooms", 22, "3.1", "3.3", "0.3"),
                food("Zucchini", 17, "1.2", "3.1", "0.3"),
                food("Eggplant", 25, "1.0", "5.9", "0.2"),
                food("Cabbage", 25, "1.3", "5.8", "0.1"),
                food("Brussels sprouts", 43, "3.4", "9.0", "0.3"),
                food("Asparagus", 20, "2.2", "3.9", "0.1"),
                food("Celery", 16, "0.7", "3.0", "0.2"),
                food("Beetroot", 43, "1.6", "9.6", "0.2"),
                food("Pumpkin", 26, "1.0", "6.5", "0.1"),
                food("Green beans", 31, "1.8", "7.0", "0.2"),
                food("Lettuce", 15, "1.4", "2.9", "0.2"),
                food("Parsnip", 75, "1.2", "18.0", "0.3"),
                food("Butternut squash", 45, "1.0", "11.7", "0.1"),
                food("Leek", 61, "1.5", "14.2", "0.3"),
                food("Artichoke", 47, "3.3", "10.5", "0.2"),
                food("Chicken breast", 165, "31.0", "0.0", "3.6"),
                food("Chicken thigh", 209, "26.0", "0.0", "10.9"),
                food("Turkey breast", 135, "30.0", "0.0", "1.0"),
                food("Ground beef, 90% lean", 176, "20.0", "0.0", "10.0"),
                food("Beef sirloin", 206, "27.0", "0.0", "10.0"),
                food("Pork loin", 196, "27.0", "0.0", "9.0"),
                food("Pork tenderloin", 143, "26.0", "0.0", "3.5"),
                food("Lamb leg", 206, "28.0", "0.0", "10.0"),
                food("Bacon", 541, "37.0", "1.4", "42.0"),
                food("Ham", 145, "21.0", "1.5", "5.5"),
                food("Salmon", 208, "20.4", "0.0", "13.4"),
                food("Atlantic cod", 82, "18.0", "0.0", "0.7"),
                food("Tuna, canned in water", 116, "25.5", "0.0", "0.8"),
                food("Sardines", 208, "24.6", "0.0", "11.5"),
                food("Shrimp", 99, "24.0", "0.2", "0.3"),
                food("Egg", 143, "12.6", "0.7", "9.5"),
                food("Egg white", 52, "10.9", "0.7", "0.2"),
                food("Whole milk", 63, "3.3", "4.9", "3.4", NutritionBasis.PER_100_MILLILITERS),
                food("Semi-skimmed milk", 52, "3.5", "4.9", "1.9", NutritionBasis.PER_100_MILLILITERS),
                food("Cheddar cheese", 403, "25.0", "1.3", "33.0"),
                food("Mozzarella", 280, "28.0", "3.1", "17.0"),
                food("Parmesan", 431, "38.0", "4.1", "29.0"),
                food("Plain Greek yogurt", 97, "9.0", "3.9", "5.0"),
                food("Plain yogurt", 61, "3.5", "4.7", "3.3"),
                food("Cottage cheese", 98, "11.1", "3.4", "4.3"),
                food("Butter", 717, "0.9", "0.1", "81.0"),
                food("Cream cheese", 342, "6.2", "4.1", "34.0"),
                food("Oats, dry", 389, "16.9", "66.3", "6.9"),
                food("Brown rice, cooked", 123, "2.7", "25.6", "1.0"),
                food("White rice, cooked", 130, "2.7", "28.2", "0.3"),
                food("Quinoa, cooked", 120, "4.4", "21.3", "1.9"),
                food("Whole wheat bread", 247, "13.0", "41.0", "4.2"),
                food("White bread", 266, "8.9", "49.0", "3.2"),
                food("Whole wheat pasta, cooked", 149, "5.8", "30.1", "0.9"),
                food("Pasta, cooked", 157, "5.8", "30.9", "0.9"),
                food("Buckwheat, cooked", 92, "3.4", "19.9", "0.6"),
                food("Barley, cooked", 123, "2.3", "28.2", "0.4"),
                food("Lentils, cooked", 116, "9.0", "20.1", "0.4"),
                food("Chickpeas, cooked", 164, "8.9", "27.4", "2.6"),
                food("Black beans, cooked", 132, "8.9", "23.7", "0.5"),
                food("Kidney beans, cooked", 127, "8.7", "22.8", "0.5"),
                food("Tofu, firm", 144, "17.3", "2.8", "8.7"),
                food("Tempeh", 192, "20.3", "7.6", "10.8"),
                food("Almonds", 579, "21.2", "21.6", "49.9"),
                food("Walnuts", 654, "15.2", "13.7", "65.2"),
                food("Peanut butter", 588, "25.0", "20.0", "50.0"),
                food("Chia seeds", 486, "16.5", "42.1", "30.7"),
                food("Sunflower seeds", 584, "20.8", "20.0", "51.5"),
                food("Olive oil", 810, "0.0", "0.0", "91.6", NutritionBasis.PER_100_MILLILITERS),
                food("Honey", 304, "0.3", "82.4", "0.0"),
                food("Dark chocolate, 70%", 598, "7.8", "45.9", "42.6"),
                food("Rye crispbread", 366, "10.0", "75.0", "3.0")
        ));
    }

    private static FoodEntity food(String name, int kcal, String protein, String carbs, String fats) {
        return food(name, kcal, protein, carbs, fats, NutritionBasis.PER_100_GRAMS);
    }

    private static FoodEntity food(
            String name, int kcal, String protein, String carbs, String fats, NutritionBasis nutritionBasis) {
        FoodEntity food = new FoodEntity();
        food.setName(name);
        food.setNutritionBasis(nutritionBasis);
        food.setKcal((short) kcal);
        food.setProtein(new BigDecimal(protein));
        food.setCarbs(new BigDecimal(carbs));
        food.setFats(new BigDecimal(fats));
        return food;
    }
}
