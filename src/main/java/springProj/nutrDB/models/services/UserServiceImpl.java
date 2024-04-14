package springProj.nutrDB.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springProj.nutrDB.data.entities.UserEntity;
import springProj.nutrDB.data.repositories.UserRepository;
import springProj.nutrDB.models.dto.UserDTO;
import springProj.nutrDB.models.exceptions.DuplicateEmailException;
import springProj.nutrDB.models.exceptions.PasswordsNotEqualException;

/**
 * Implementation of {@link springProj.nutrDB.models.services.UserService}
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * This service manipulates the persistent data in model solely through this repository object.
     * Autowired (instance provided by dependency injection - field injection).
     * TODO switch to constructor injection
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Autowired - instance provided by dependency injection.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(UserDTO user, boolean isAdmin) {
        if (!user.getPassword().equals(user.getPasswordConfirmation())) // check if password equals confirmation
            throw new PasswordsNotEqualException();

        UserEntity userEntity = new UserEntity();
        // transfer the data from DTO to entity
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setAdmin(isAdmin);

        // attempt to save a new user - may fail for the reason of user email already being used
        try {
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException();
        }
    }

    /**
     * Doc copied from org.springframework.security.core.userdetails.UserDetailsService:
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username, " + username + " not found."));
    }

}
