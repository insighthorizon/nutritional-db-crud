package springProj.nutrDB.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest()
                        .permitAll()
                )
                .formLogin(withDefaults()
//                        (authz) -> authz
//                        .loginPage("/account/login")
//                        .loginProcessingUrl("/account/login")
//                        .defaultSuccessUrl("/foods", true)
//                        .usernameParameter("email")
//                        .permitAll()
                )
                .logout(withDefaults()
//                        (authz) -> authz
//                        .logoutUrl("/account/logout")
                )
                .build();


    }
}
