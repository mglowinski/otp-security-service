package com.mglowinski.otp;

import com.mglowinski.otp.model.Role;
import com.mglowinski.otp.model.User;
import com.mglowinski.otp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class OtpApplication {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OtpApplication(UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(OtpApplication.class, args);
    }

    @PostConstruct
    public void init() {
        User user = new User(
                "Mock first name",
                "Mock last name",
                "user",
                passwordEncoder.encode("pw"),
                "mck00@o2.pl",
                new HashSet<>(
                        Arrays.asList(
                                new Role("ROLE_USER"),
                                new Role("ROLE_ADMIN"))));

        boolean isUserExist = userService.getUserByUsername(user.getUsername()).isPresent();

        if (!isUserExist) {
            userService.createUser(user);
        }
    }

}
