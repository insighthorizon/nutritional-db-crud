package springProj.nutrDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class prepresents the spring boot application.
 * With the annotation and SpringApplication :: run, we bootstrap and lunch our spring application.
 */
@SpringBootApplication
public class NutrDBApplication {

    /**
     * Main method launches the Spring Boot application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
// generation of test BCrypt hash:
//        final String TEST_PASSWORD = "12345";
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println("Test BCrypt hash for password \"" + TEST_PASSWORD + "\":");
//        System.out.println(encoder.encode(TEST_PASSWORD));

        SpringApplication.run(NutrDBApplication.class, args);

    }
}

