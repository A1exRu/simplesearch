package home.a1exru.simpleclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableAutoConfiguration
public class SimpleClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleClientApplication.class, args);
    }

    @Bean
    @Profile("default")
    public CommandService commandService() {
        return new CommandService();
    }
}
