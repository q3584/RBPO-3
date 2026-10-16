package com.javalab.lab1.Service;

import org.springframework.stereotype.Service;
import com.javalab.lab1.Entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService 
{
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong(1);

    public List<User> getAllUsers()
    {
        return new ArrayList<>(users.values());
    }

    public User getUserbyId(Long id)
    {
        return users.get(id);
    }

    public User postUser(User user)
    {
        Long id = idCount.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public User patchUser(Long id, User patchedUser)
    {
        if (!users.containsKey(id)) {return null;}
        patchedUser.setId(id);
        users.put(id, patchedUser);
        return patchedUser;
    }
    
    public void deleteUser(Long id)
    {
        users.remove(id);
    }
}
