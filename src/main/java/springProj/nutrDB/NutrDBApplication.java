package springProj.nutrDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NutrDBApplication {
    public static void main(String[] args) {
        // vygenerovani hashe pro heslo TEST_PASSWORD pro pripad ze si chceme uzivatele vytvorit v databazi manualne
        // muzeme tak testovat prihlasovani jeste predtim nez vubec implementujeme template pro login a nez mame registraci implementovanou ve sluzbe a controlleru
        // staci mit implementovano UserDetails a UserDetailsService
        final String TEST_PASSWORD = "12345";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Test BCrypt hash for password \"" + TEST_PASSWORD + "\":");
        System.out.println(encoder.encode(TEST_PASSWORD));


        SpringApplication.run(NutrDBApplication.class, args);
    }
}
