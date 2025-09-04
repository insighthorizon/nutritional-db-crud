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
     * no dependencies, no fields<br>
     * To avoid Javadoc warning. This is equivalent to auto-generated default constructor.
     */
    public NutrDBApplication() {
    }

    /**
     * Main method launches the Spring Boot application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(NutrDBApplication.class, args);
    }
}

