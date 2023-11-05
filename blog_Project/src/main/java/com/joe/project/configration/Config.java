package com.joe.project.configration;

import com.joe.project.dto.UserDto;
import com.joe.project.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService){
        return args -> {
            userService.createUser(new UserDto(null, "Ahmed", "Ahmed@email", "123"));
            userService.createUser(new UserDto(null, "Yusuf", "Yusuf@email", "456"));
            userService.createUser(new UserDto(null, "Ali", "Ali@email", "789"));

            userService.createRole(null, "ROLE_USER");
            userService.createRole(null, "ROLE_MANAGER");
            userService.createRole(null, "ROLE_ADMIN");

            userService.userRole("Yusuf", "ROLE_USER");
            userService.userRole("Ahmed", "ROLE_MANAGER");
            userService.userRole("Ali", "ROLE_ADMIN");
        };
    }

}
