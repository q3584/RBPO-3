package com.javalab.lab1;

import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping ("/test")
public class TestController {
    @GetMapping("/text")
    public String getText() {
        return "пример текста";
    }

    @GetMapping ("/number")
    public int getnumber() {
        return 32;
    }
}
