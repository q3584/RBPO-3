package com.javalab.lab1.Controllers;

import java.util.Map;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/api") 
public class CSRFController {
    @GetMapping("/csrf")
    public Map<String, String> getCsrfToken(CsrfToken csrfToken)
    {
        return Map.of("token", csrfToken.getToken());
    }
}
