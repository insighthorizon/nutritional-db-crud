package springProj.nutrDB.models.exceptions;

/**
 * Represents a detected attempt to read a food entry which doesn't exist from the database.
 * This would happen for example if multiple users access the database and the original entry was deleted before the request for this could be finished.
 */
public class FoodNotFoundException extends RuntimeException{
    /**
     * no dependencies, no fields<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public FoodNotFoundException() {}
}
