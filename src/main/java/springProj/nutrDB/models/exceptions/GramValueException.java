package springProj.nutrDB.models.exceptions;

/**
 * This exception indicates that the user entered gram values that add up to more than 100 g
 * - Which is nonsensical, because we are working with grams per 100 g
 */
public class GramValueException extends  RuntimeException {
}
