package com.javalab.lab1.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.UUID;

import com.javalab.lab1.SessionStatus;
@Entity 
@Table(name = "user_sessions")
@Getter 
@Setter 
@NoArgsConstructor 
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "access_token", nullable = false, columnDefinition = "TEXT")
    private String accessToken;
    @Column(name = "refresh_token", nullable = false, unique = true, columnDefinition = "TEXT")
    private String refreshToken;
    @Column(name = "access_token_expiry", nullable = false)
    private Instant accessTokenExpiry;
    @Column(name = "refresh_token_expiry", nullable = false)
    private Instant refreshTokenExpiry;
    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private SessionStatus status;
}
