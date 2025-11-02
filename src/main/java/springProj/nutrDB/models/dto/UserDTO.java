package springProj.nutrDB.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Defines the Data-transfer object for UserEntity - for transfering data between model and view. It's structure
 * reflects {@link springProj.nutrDB.data.entities.UserEntity}.<br><br>
 * The annotations on private fields of this class specify validation constraints of the user intput together with
 * error messages about when the validation constraint gets broken. WHEN validation is applied is defined by another
 * annotation in a controller.<br>
 * - functionality provided by Jakarta Validation API (Hibernate implementation).
 */
public class UserDTO {

    /**
     * no dependencies, default field values<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public UserDTO() {}

    /**
     * user email (unique index in the databae table)
     * validation contraints: has to be an email address, can't be just blank chars
     */
    @Email(message = "Enter a valid email address.")
    @NotBlank(message = "Email address missing.")
    private String email;

    /**
     * user password (provided by the user, not the hash yet)
     * validation constraints: can't contain only blanks, minimum 5 characters long
     * (Yes, that's short. This is a test/demo application.)
     */
    @NotBlank(message = "Password missing.")
    @Size(min = 5, message = "The password needs to have at least 5 characters.")
    private String password;

    /**
     * user password confirmation - user needs to enter his password for the second time to confirm
     * that he didn't make a mistake when typing it first. Their equality is checked in
     * {@link springProj.nutrDB.models.services.UserService#create(UserDTO, boolean)}.
     * validation constraints: can't be blank, minumum size of 5 characters
     * (Yes, that's short. This is a test/demo application.)
     */
    @NotBlank(message = "Password missing.")
    @Size(min = 5, message = "The password needs to have at least 5 characters.")
    private String passwordConfirmation;


    /**
     * Return user email (unique index in the databae table)<br>
     * validation contraints: has to be an email address, can't be just blank chars
     * @return users email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user email (unique index in the databae table)<br>
     * validation contraints: has to be an email address, can't be just blank chars
     * @param email users email
     */
    @SuppressWarnings("unused")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return user password (provided by the user, not the hash yet)<br>
     * validation constraints: can't contain only blanks, minimum 5 characters long
     * @return user-provided password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user password (provided by the user, not the hash yet)<br>
     * validation constraints: can't contain only blanks, minimum 5 characters long
     * @param password user-provided password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * user password confirmation - user needs to enter his password for the second time to confirm
     * that he didn't make a mistake when typing it first. Their equality is checked in
     * {@link springProj.nutrDB.models.services.UserService#create(UserDTO, boolean)}.<br>
     * validation constraints: can't be blank, minumum size of 5 characters
     * (Yes, that's short. This is a test/demo application.)
     * @return password pass cofirmation
     */
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    /**
     * user password confirmation - user needs to enter his password for the second time to confirm
     * that he didn't make a mistake when typing it first. Their equality is checked in
     * {@link springProj.nutrDB.models.services.UserService#create(UserDTO, boolean)}.<br>
     * validation constraints: can't be blank, minumum size of 5 characters
     * (Yes, that's short. This is a test/demo application.)
     * @param passwordConfirmation pass confirmation
     */
    @SuppressWarnings("unused")
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
