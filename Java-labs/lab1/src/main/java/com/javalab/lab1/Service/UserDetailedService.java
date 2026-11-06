package com.javalab.lab1.Service;

import com.javalab.lab1.Entities.User;
import com.javalab.lab1.Repository.UserRep;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service 
public class UserDetailedService implements UserDetailsService{
    private final UserRep userRep;

    public UserDetailedService(UserRep userRep) {
        this.userRep = userRep;
    }
    @Override 
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRep.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found: " + email));
        return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .authorities(user.getRole())
        .disabled(!user.isEnabled())
        .build();
    }
}
