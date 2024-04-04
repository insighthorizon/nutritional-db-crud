package springProj.nutrDB.models.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import springProj.nutrDB.models.dto.UserDTO;

public interface UserService extends UserDetailsService {
    void create(UserDTO user, boolean isAdmin);
}
