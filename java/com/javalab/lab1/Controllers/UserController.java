package com.javalab.lab1.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.javalab.lab1.Entities.User;
import com.javalab.lab1.Service.UserService;
import java.util.List;
import java.util.Map;

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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserbyId(@PathVariable Long id)
    {
        return ResponseEntity.ok(userService.getUserByID(id));
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> request){
        try {
            User user = new User();
            user.setName((String) request.get("name"));
            user.setEmail((String) request.get("email"));
            user.setRole((String) request.get("role"));

            String password = (String) request.get("password");
            User createdUser = userService.creatUser(user, password);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> request){
        User existingUser = userService.getUserByID(id);
        if (request.containsKey("name")){
            existingUser.setName((String) request.get("name"));
        }
        if (request.containsKey("email")){
            existingUser.setEmail((String) request.get("email"));
        }
        if (request.containsKey("role")){
            existingUser.setRole((String) request.get("role"));
        }
        if (request.containsKey("enabled")){
            existingUser.setEnabled((Boolean) request.get("enabled"));
        }
        String newPassword = (String) request.get("password");
        User updatedUser = userService.updateUser(id, existingUser, newPassword);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
