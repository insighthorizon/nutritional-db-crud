package springProj.nutrDB.models.exceptions;

/**
 * Represents a detected attempt to create a new user with an email which is already used.<br>
 * - a checked exception (compiler will demand that it is passed through the method calls - having "throws"
 * in method header - until it gets handled in try-catch block.
 */
public class DuplicateEmailException extends Exception {
    /**
     * no dependencies, no fields<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public DuplicateEmailException() {}
}
