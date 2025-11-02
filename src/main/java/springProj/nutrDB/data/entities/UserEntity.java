package springProj.nutrDB.data.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springProj.nutrDB.models.dto.UserDTO;

import java.util.Collection;
import java.util.List;

/**
 * This class represents a user in the persistance layer (one row of table in the database).
 * A user object has in id, email, password hash (not the password entered by user)
 * and a flag specifying whether it is an admin.<br>
 * This class has to also implement {@link UserDetails} interface, as it allows spring security to recognize
 * and use objects of this class for authentication and authorization.
 * Check the doc of the implemented Interface for more info on the implemented methods.
 * <br><br>
 * The annotations in this class specify representation of FoodEntity in the MySQL database. (See the private fields in source code.)<br>
 * Conversion between object and database row is based on ORM (Object-relational mapping)
 * and is provided by Jakarta Persistence API (Hibernate implementation). Both annotation and field data type
 * have an effect on how it is represented as a database column.
 * A table database table with corresponding structure and name is automatically created at start of spring app if it doesn't exist yet.
 */
@Entity
public class UserEntity implements UserDetails {

    /**
     * no dependencies, default field values<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public UserEntity() {}

    /**
     * ID - primary key
     * represented as primary key, bigint, not null, auto_increment in SQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    /**
     * user email - unique
     * represented as unique, varchar(255), not null in SQL
     * Hibernate:
     *     alter table user_entity
     *        drop index if exists UK_4xad1enskw4j1t2866f7sodrx
     * Hibernate:
     *     alter table user_entity
     *        add constraint UK_4xad1enskw4j1t2866f7sodrx unique (email)
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * password hash - the user provided original isn't saved in the database (security 101)
     * A hash is usually generated during user registration in {@link springProj.nutrDB.models.services.UserService#create(UserDTO, boolean)}
     * represented as varchar(255), not null in SQL
     */
    @Column(nullable = false)
    private String password; // ukladame hash

    /**
     * a flag signifying whether the user has the admin role ("ROLE_ADMIN")
     * represented as bit, not null in SQL
     */
    @Column(nullable = false)
    private boolean isAdmin;


    // region: UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // account roles for authorization
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + (isAdmin ? "ADMIN" : "USER"));
        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
    // endregion (UserDetails methods)

    // The rest of standard getters and setters
    /**
     * Return user ID - primary key<br>
     * represented as primary key, bigint, not null, auto_increment in SQL
     * @return user ID
     */
    @SuppressWarnings("unused")
    public long getUserId() {
        return userId;
    }

    /**
     * Return user email - unique<br>
     * represented as unique, varchar(255), not null in SQL
     * @return user email
     */
    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    /**
     * Set user email - unique<br>
     * represented as unique, varchar(255), not null in SQL
     * @param email user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set user password - a hash of what user provided,
     * the user provided original isn't saved in the database (security 101)
     * A hash is usually generated during user registration in {@link springProj.nutrDB.models.services.UserService#create(UserDTO, boolean)}<br>
     * represented as varchar(255), not null in SQL
     * @param password password hash
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the flag signifying whether the user has the admin role ("ROLE_ADMIN")<br>
     * represented as bit, not null in SQL
     * @return admin role flag
     */
    @SuppressWarnings("unused")
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Set the flag signifying whether the user has the admin role ("ROLE_ADMIN")<br>
     * represented as bit, not null in SQL
     * @param admin admin role flag
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
