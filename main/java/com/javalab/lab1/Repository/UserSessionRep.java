package com.javalab.lab1.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javalab.lab1.SessionStatus;
import com.javalab.lab1.Entities.UserSession;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

public interface UserSessionRep extends JpaRepository<UserSession, UUID>
{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM UserSession s WHERE s.refreshToken = :refreshToken")
    Optional<UserSession> findByRefreshTokenForUpdate(@Param("refreshToken") String refreshToken);
    Optional<UserSession> findByRefreshTokenAndStatus(String refreshToken, SessionStatus status);
    boolean existsByRefreshToken(String refreshToken);
}
