package springProj.nutrDB.models.exceptions;

/**
 * Represents a detected attempt of user to create a new account while failing to confirm password (the two password fields are not equal).<br>
 * - a checked exception (compiler will demand that it is passed through the method calls - having "throws"
 * in method header - until it gets handled in try-catch block.)
 */
public class PasswordsNotEqualException extends Exception{
    /**
     * no dependencies, no fields<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public PasswordsNotEqualException() {}
}
