package com.javalab.lab1.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javalab.lab1.Entities.User;
import com.javalab.lab1.Service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController 
{
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers()
    {
        return userService.getallUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserbyId(@PathVariable Long id)
    {
        return userService.getUserByID(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user)
    {
        return userService.updUser(id, user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
