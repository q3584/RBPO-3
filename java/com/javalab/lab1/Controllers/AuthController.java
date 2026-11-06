package com.javalab.lab1.Controllers;
import com.javalab.lab1.DTO.RegRequest;
import com.javalab.lab1.DTO.RegResponse;
import com.javalab.lab1.Entities.User;
import com.javalab.lab1.Service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) 
    {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegResponse> register(@RequestBody RegRequest request)
    {
    User user = authService.registerUser(
        request.name(),
        request.email(),
        request.password()
    );

    RegResponse response = new RegResponse(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getRole()
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
}

