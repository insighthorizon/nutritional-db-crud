package springProj.nutrDB.models.exceptions;

/**
 * Represents a detected attempt to read a food entry which doesn't exist from the database.
 * This would happen for example if multiple users access the database and the original entry was deleted before the request for this could be finished.<br>
 * - a checked exception (compiler will demand that it is passed through the method calls - having "throws"
 * in method header - until it gets handled in try-catch block.
 */
public class FoodNotFoundException extends Exception{
    /**
     * no dependencies, no fields<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public FoodNotFoundException() {}
}
