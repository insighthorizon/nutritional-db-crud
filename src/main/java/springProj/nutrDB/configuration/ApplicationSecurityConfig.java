package springProj.nutrDB.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationSecurityConfig {

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
                        .defaultSuccessUrl("/foods?login", true)
                        .usernameParameter("email") // to receive username from the form field with the attribute name="email"
                        .passwordParameter("password") // to receive the password from the form field with the attribute name="password" (this line of code is redundant when name="password")
                        .permitAll())
                .logout(auth -> auth
                        .logoutUrl("/account/logout")
                        .logoutSuccessUrl("/account/login?logout")
                )
                .build(); // pro dokonceni nastavovani objektu http
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
