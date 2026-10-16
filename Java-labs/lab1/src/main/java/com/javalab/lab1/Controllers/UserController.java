package com.javalab.lab1.Controllers;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<User>> getAllUsers()
    {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserbyId(@PathVariable Long id)
    {
        User user = userService.getUserbyId(id);
        if (user == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody User user) {
        User createdUser = userService.postUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User user)
    {
        User patchedUser = userService.patchUser(id, user);
        if (patchedUser == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patchedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
