package com.javalab.lab1.Service;

import com.javalab.lab1.Entities.User;
import com.javalab.lab1.Repository.UserRep;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service 
public class UserService{
    private final UserRep userRep;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRep userRep, PasswordEncoder passwordEncoder) {
        this.userRep = userRep;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRep.findAll();
    }
    public User getUserByID(Long id){
        return userRep.findById(id).orElseThrow(()-> new IllegalArgumentException("User with ID " + id + " not found."));
    }
    public User creatUser(User user, String rawPassword){
        if (userRep.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("User with such email is already exists");
        }
        if (rawPassword != null && !rawPassword.isBlank()){
            user.setPassword(passwordEncoder.encode(rawPassword));
        } else
        {
            throw new IllegalArgumentException("Password cant be empty");
        }
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        return userRep.save(user);
    }
    public User updateUser(Long id, User updatedDetails, String newPass)
    {
        User existingUser = getUserByID(id);
        if (!existingUser.getEmail().equals(updatedDetails.getEmail())){
            if (userRep.findByEmail(updatedDetails.getEmail()).isPresent()){
                throw new IllegalArgumentException("This email is already taken.");
            }
            existingUser.setEmail(updatedDetails.getEmail());
        }
        existingUser.setName(updatedDetails.getName());
        existingUser.setEnabled(updatedDetails.isEnabled());
        if (updatedDetails.getRole() != null && !updatedDetails.getRole().isBlank()){
            existingUser.setRole(updatedDetails.getRole());
        }
        if(newPass != null && !newPass.isBlank()){
            existingUser.setPassword(passwordEncoder.encode(newPass));
        }
        return userRep.save(existingUser);
    }
    public void deleteUser(Long id)
    {
        if (!userRep.existsById(id)){
            throw new IllegalArgumentException("User with ID " + id + " not found.");
        }
        userRep.deleteById(id);
    }
}
