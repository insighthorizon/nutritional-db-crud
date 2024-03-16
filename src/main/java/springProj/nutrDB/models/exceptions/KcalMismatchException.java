package springProj.nutrDB.models.exceptions;

/**
 * This exception indicates that the user entered caloric value that cannot correctly match
 * the macronutrient values in grams.
 * Formula:
 * energy = (4 * protein + 4 * carbs + 9 * fats) +- 20%
 * energy [kcal], protein [g], carbs [g], fats [g]
 */
public class KcalMismatchException extends RuntimeException {
}
