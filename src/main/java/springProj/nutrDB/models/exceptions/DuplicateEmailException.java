package springProj.nutrDB.models.exceptions;

/**
 * Represents a detected attempt to create a new user with an email which is already used.
 */
public class DuplicateEmailException extends RuntimeException {
    /**
     * no dependencies, no fields<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public DuplicateEmailException() {}
}
