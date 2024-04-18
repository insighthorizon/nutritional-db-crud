package springProj.nutrDB.models.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import springProj.nutrDB.models.dto.UserDTO;

/**
 * This interface specifies a service which exposes operations for manipulation of user data
 * (entities in the model layer) to the controller layer.<br>
 * UserDetailsService needs to be implemented so that spring security can use it for loading user data from the database.
 */
public interface UserService extends UserDetailsService {

    /**
     * Attempt to create a new user.
     * @param user User data of the new user being created.
     * @param isAdmin flag for the admin role
     */
    void create(UserDTO user, boolean isAdmin);

}
