package com.javalab.lab1.Service;

import com.javalab.lab1.DTO.TokenResponse;
import com.javalab.lab1.Entities.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserService userService;
    private final TokenPairService tokenPairService;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserService userService, TokenPairService tokenPairService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenPairService = tokenPairService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional 
    public TokenResponse registerUser(String name, String email, String rawPassword, String deviceId)
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
        User savedUser = userService.createUser(user, rawPassword);
        return tokenPairService.issueTokenPair(savedUser, deviceId);
    }
    public TokenResponse loginUser(String email, String rawPassword, String deviceId)
    {
        User user = userService.getUserByEmail(email);
        if (!passwordEncoder.matches(rawPassword, user.getPassword()))
        {
            throw new IllegalArgumentException("Invalid email or password");
        }
        if (!user.isEnabled())
        {
            throw new IllegalArgumentException("User is disabled");
        }
        return tokenPairService.issueTokenPair(user, deviceId);
    }
    public TokenResponse refreshToken(String refreshToken)
    {
        if (refreshToken == null || refreshToken.isBlank())
        {
            throw new IllegalArgumentException("Refresh token is required");
        }
        return tokenPairService.refreshTokenPair(refreshToken);
    }
}
