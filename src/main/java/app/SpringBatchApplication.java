package app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class SpringBatchApplication {

    // entry point of the application
    public static void main(String[] args) {
        try {
            SpringApplication.run(SpringBatchApplication.class, args);
        } catch (Exception exception) {
            System.out.println("Caught an exception");
            System.out.println(Arrays.toString(exception.getStackTrace()));
        }
    }

    @Bean
    //prints the list of available beans
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = applicationContext.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}
