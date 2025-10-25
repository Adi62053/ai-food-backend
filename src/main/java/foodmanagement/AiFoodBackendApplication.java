package foodmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "foodmanagement")  // Ensure this line
@EntityScan("foodmanagement.model")
@EnableJpaRepositories("foodmanagement.repository")
public class AiFoodBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiFoodBackendApplication.class, args);
    }
}