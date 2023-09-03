package com.example.autologin;

import com.example.autologin.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.example.autologin"})
@EntityScan(basePackages = { "com.example.autologin.entity"})
public class AutologinApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutologinApplication.class, args);
    }
   /* @Bean
    CommandLineRunner start(UserRepository userRepository) {
        return users->{
        };
    }*/

}
