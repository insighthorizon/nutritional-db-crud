package springProj.nutrDB.models.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import springProj.nutrDB.models.dto.UserDTO;
import springProj.nutrDB.models.exceptions.DuplicateEmailException;
import springProj.nutrDB.models.exceptions.PasswordsNotEqualException;

/**
 * This interface specifies a service which exposes operations for manipulation of user data
 * (entities in the model layer) to the controller layer.<br>
 * {@link UserDetailsService} needs to be implemented so that spring security can use it for loading user data from the database.
 * Check the doc of the implemented Interface for more info on the implemented methods.
 */
public interface UserService extends UserDetailsService {

    /**
     * Attempt to create a new user (mail fail if email already taken).
     * The password string provided by the client is contained in the UserDTO.
     * It will be checked whether the user typed the same password twice (confirmation against a password typo).
     * If the check dosnt't pass, the new user isn't created and {@link springProj.nutrDB.models.exceptions.PasswordsNotEqualException}
     * is raised.
     * The user provided password string will to be converted to hash using BCrypt.
     * Database stores only hash, not the user provided password string. (Security 101)<br>
     * If the user emial is already taken, the new user isn't created and {@link springProj.nutrDB.models.exceptions.DuplicateEmailException}
     * is raised.
     * @param user User data of the new user being created.
     * @param isAdmin flag for the admin role
     * @throws PasswordsNotEqualException handled in a controller.
     * @throws DuplicateEmailException handled in a controller.
     */
    void create(UserDTO user, boolean isAdmin) throws PasswordsNotEqualException, DuplicateEmailException;

}
