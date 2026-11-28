package springProj.nutrDB.models.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import springProj.nutrDB.data.entities.NutritionBasis;
import springProj.nutrDB.models.dto.FoodDTO;

import java.math.BigDecimal;

/**
 * Implements the rules for {@link ValidFoodNutrition}. The type arguments to
 * {@code ConstraintValidator} name the constraint annotation and the type of value it validates.
 * This validator receives the whole {@link FoodDTO}, so it can read several fields together.
 *
 * @see <a href="https://docs.hibernate.org/validator/9.1/reference/en-US/html_single/#section-class-level-constraints">Hibernate Validator example of a class-level annotation and its validator</a>
 * @see <a href="https://docs.hibernate.org/validator/9.1/reference/en-US/html_single/#section-custom-property-paths">Hibernate Validator example of reporting an error against a field with addPropertyNode</a>
 */
public class FoodNutritionValidator implements ConstraintValidator<ValidFoodNutrition, FoodDTO> {
    private static final BigDecimal MAX_GRAMS_PER_100_GRAMS = BigDecimal.valueOf(100);
    // FAO reports molasses density near 1.5 g/ml: https://www.fao.org/4/a0242e/a0242e01.pdf
    private static final BigDecimal MAX_GRAMS_PER_100_ML = BigDecimal.valueOf(150);
    // Conservative factors for the minimum energy check.
    private static final BigDecimal KCAL_PER_GRAM_OF_PROTEIN_OR_CARBS = new BigDecimal("3.5");
    private static final BigDecimal KCAL_PER_GRAM_OF_FAT = new BigDecimal("8.3");

    /** Creates a validator for Jakarta Validation to use when checking a food DTO. */
    public FoodNutritionValidator() {}

    @Override
    public boolean isValid(FoodDTO food, ConstraintValidatorContext context) {
        if (food == null) {
            return true;
        }

        NutritionBasis basis = food.getNutritionBasis();
        BigDecimal protein = food.getProtein();
        BigDecimal carbs = food.getCarbs();
        BigDecimal fat = food.getFats();
        if (basis == null || protein == null || carbs == null || fat == null) {
            return true; // Missing values are reported by the individual field constraints.
        }

        BigDecimal massLimit = switch (basis) {
            case PER_100_GRAMS -> MAX_GRAMS_PER_100_GRAMS;
            case PER_100_MILLILITERS -> MAX_GRAMS_PER_100_ML;
        };
        BigDecimal proteinAndCarbs = protein.add(carbs);
        BigDecimal macroMass = proteinAndCarbs.add(fat);
        // The form's carbohydrate value is used as net carbs for this estimate.
        BigDecimal macroEnergy = proteinAndCarbs.multiply(KCAL_PER_GRAM_OF_PROTEIN_OR_CARBS)
                .add(fat.multiply(KCAL_PER_GRAM_OF_FAT));

        boolean massTooHigh = macroMass.compareTo(massLimit) > 0;
        // Alcohol, fibre, and other components can contribute to the declared kcal.
        boolean energyTooHigh = macroEnergy.compareTo(BigDecimal.valueOf(food.getKcal())) > 0;
        if (!massTooHigh && !energyTooHigh) {
            return true;
        }

        // Replace the annotation's fallback message with a violation for each failed rule.
        // addPropertyNode assigns the error to a field so Spring can display it beside that form input.
        context.disableDefaultConstraintViolation();
        if (massTooHigh) {
            context.buildConstraintViolationWithTemplate(
                            "Protein, carbohydrates, and fat total %s g. The limit is %s g %s."
                                    .formatted(macroMass.toPlainString(), massLimit.toPlainString(), basis.getLabel()))
                    .addPropertyNode("nutritionBasis")
                    .addConstraintViolation();
        }
        if (energyTooHigh) {
            context.buildConstraintViolationWithTemplate(
                            "The entered energy is below the estimated minimum of %s kcal %s."
                                    .formatted(macroEnergy.toPlainString(), basis.getLabel()))
                    .addPropertyNode("kcal")
                    .addConstraintViolation();
        }
        return false;
    }
}
