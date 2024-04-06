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

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void create(UserDTO user, boolean isAdmin) {
        if (!user.getPassword().equals(user.getPasswordConfirmation())) // test if passwords equal
            throw new PasswordsNotEqualException();

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setAdmin(isAdmin);

        // attempt to save a new user
        try {
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username, " + username + " not found."));
    }

}
