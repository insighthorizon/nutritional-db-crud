package springProj.nutrDB.models.exceptions;

/**
 * Represents a detected attempt of user to create a new account while failing to confirm password (the two password fields are not equal).
 */
public class PasswordsNotEqualException extends RuntimeException{
    /**
     * no dependencies, no fields<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public PasswordsNotEqualException() {}
}
