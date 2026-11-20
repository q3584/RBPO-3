package com.javalab.lab1.Configuration;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.javalab.lab1.Entities.User;
import com.javalab.lab1.Repository.UserRep;
import com.javalab.lab1.Service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class JWTFilter extends OncePerRequestFilter{
    private final JwtService jwtService;
    private final UserRep userRep;

    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        try {
            jwtService.validateAccessToken(token);
            String email = jwtService.extractEmail(token);
            User user = userRep.findByEmail(email).orElse(null);
            if (user != null && user.isEnabled())
            {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority(user.getRole())));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (RuntimeException exception)
        {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
