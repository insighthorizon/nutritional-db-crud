package springProj.nutrDB.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.AbstractSecurityBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * security (authentication, authorization) configuration class
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // to make our @Security annotations work
public class ApplicationSecurityConfig {

    /**
     * No dependencies
     */
    public ApplicationSecurityConfig(){}

    /**
     * Global authentication and authorization configuration;
     * (@Bean - This is how we specify Spring IoC should get its sole instance of SecurityFilterChain.)
     * @param http an object with methods for building security filter chain
     * @return security filter chain - resulting configuration applied to all http requests
     * @throws Exception {@link AbstractSecurityBuilder#build() abstractSecurityBuilder::build} may throw.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // access whill be given freely to all unless we change that explicitly for a given https request
                        .anyRequest()
                        .permitAll())
                .formLogin(auth -> auth
                        // we redirect unsigned user to login page
                        .loginPage("/account/login")
                        .loginProcessingUrl("/account/login")
                        .defaultSuccessUrl("/foods?login", true) // URL parameter login gives information that we were sent to /foods after succesful login
                        .usernameParameter("email") // to receive username from the form field with the attribute name="email"
                        .passwordParameter("password") // to receive the password from the form field with the attribute name="password" (this line of code is redundant when name="password")
                        .permitAll()) // everyone will have access to login page
                .logout(auth -> auth
                        .logoutUrl("/account/logout")
                        .logoutSuccessUrl("/account/login?logout") // URL parameter tells us that we reached /account/login after succesful logout
                )
                .build();
    }

    /**
     * Creates instance of PasswordEncoder for IoC dependency injection;
     * We use BCrypt for encoding (hashing) passwords.;
     * (@Bean - This is how we specify Spring IoC should get its sole instance of PasswordEncoder.)
     * @return instance of PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
