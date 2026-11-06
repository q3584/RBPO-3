package com.javalab.lab1.Service;

import com.javalab.lab1.Entities.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }
    public User registerUser(String name, String email, String rawPassword)
    {
        if (name == null || name.isBlank())
        {
            throw new IllegalArgumentException("Name cant be empty");
        }
        if (email == null || email.isBlank() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+$"))
        {
            throw new IllegalArgumentException("Email is incorrect or empty");
        }
        if (rawPassword == null || rawPassword.length() < 8 || !rawPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"))
        {
            throw new IllegalArgumentException("Password is too weak. " + "It should have at least 8 symbols, " + "big and small letters, and also numbers");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userService.creatUser(user, rawPassword);
    }
}
