package springProj.nutrDB.models.services;

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
 * The only implementation of {@link springProj.nutrDB.models.services.UserService}
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Constructor used by Spring IoC container to instantiate this class and
     * to inject dependencies (via constructor parameters).
     * This class has only one constructor, so no @Autowired annotation needed.
     * @param userRepository instance provided by dependency injection
     * @param passwordEncoder instance provided by dependency injection
     */
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This service is used to manipulate the persistent data in model solely through this repository object.
     */
    private final UserRepository userRepository;

    /**
     * Needed for conversion from user provided password string to its hash encoding.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(UserDTO user, boolean isAdmin) throws PasswordsNotEqualException, DuplicateEmailException {
        if (!user.getPassword().equals(user.getPasswordConfirmation())) // check if password string equals the confirmation string
            throw new PasswordsNotEqualException();

        UserEntity userEntity = new UserEntity();
        // transfer the data from DTO to entity
        userEntity.setEmail(user.getEmail());
        // convert the user provided password string to its hash using the PasswordEncoder (BCrypt)
        // we store hash, not the user provided password string
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
     * UserDetailsService needs to be implemented so that spring security can use it for loading user data from the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username, " + username + " not found."));
    }

}
