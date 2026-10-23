package com.javalab.lab1.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.javalab.lab1.Entities.User;
import com.javalab.lab1.Repository.UserRep;

@Service
public class UserService 
{
    private final UserRep userRep;

    public UserService(UserRep userRep) {
        this.userRep = userRep;
    }

    public List<User> getallUsers() {
        return userRep.findAll();
    }

    public Optional<User> getUserByID(Long id) {
        return userRep.findById(id);
    }

    public User createUser(User user) {
        return userRep.save(user);
    }

    public User updUser(Long id, User updatedUser) {
        return userRep.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            return userRep.save(user);
        }).orElseThrow(() -> new RuntimeException("Пользователь с ID " + id + " не найден"));
    }

    public void deleteUser(Long id) {
        userRep.deleteById(id);
    }
}
