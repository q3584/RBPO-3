package com.javalab.lab1.Controllers;
import com.javalab.lab1.DTO.TokenResponse;
import com.javalab.lab1.DTO.Reg.RegRequest;
import com.javalab.lab1.DTO.LoginRequest;
import com.javalab.lab1.DTO.RefreshRequest;
import com.javalab.lab1.Service.AuthService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) 
    {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegRequest request, @RequestHeader(value = "X-Device-Id", required = false) String deviceId)
    {
        TokenResponse response = authService.registerUser(request.name(), request.email(), request.password(), deviceId);
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.AUTHORIZATION, "Bearer " + response.accessToken()).body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request, @RequestHeader(value = "X-Device-Id", required = false) String deviceId)
    {
        TokenResponse response = authService.loginUser(request.email(), request.password(), deviceId);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + response.accessToken()).body(response);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request)
    {
        TokenResponse response = authService.refreshToken(request.refreshToken());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + response.accessToken()).body(response);
    }
}


