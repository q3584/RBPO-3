package com.javalab.lab1.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.jsonwebtoken.Claims;

import com.javalab.lab1.SessionStatus;
import com.javalab.lab1.DTO.TokenResponse;
import com.javalab.lab1.Entities.User;
import com.javalab.lab1.Entities.UserSession;
import com.javalab.lab1.Exceptions.InvalidRefreshToken;
import com.javalab.lab1.Exceptions.RefreshTokenReuseException;
import com.javalab.lab1.Repository.UserSessionRep;
import com.javalab.lab1.Repository.UserRep;

import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Service 
@RequiredArgsConstructor 
public class TokenPairService {
    private final JwtService jwtService;
    private final UserSessionRep userSessionRep;
    private final UserRep userRep;

    @Transactional 
    public TokenResponse issueTokenPair(User user, String deviceId)
    {
        String username = user.getEmail();
        String accessToken = jwtService.generateAccessToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);
        Claims accessClaims = jwtService.validateAccessToken(accessToken);
        Claims refreshClaims = jwtService.validateRefreshToken(refreshToken);

        UserSession session = new UserSession();
        session.setUsername(username);
        session.setDeviceId(deviceId);
        session.setAccessToken(accessToken);
        session.setRefreshToken(refreshToken);
        session.setAccessTokenExpiry(Instant.ofEpochSecond(accessClaims.getExpiration().toInstant().getEpochSecond()));
        session.setRefreshTokenExpiry(Instant.ofEpochSecond(refreshClaims.getExpiration().toInstant().getEpochSecond()));
        session.setStatus(SessionStatus.ACTIVE);
        userSessionRep.save(session);
        return new TokenResponse(accessToken, refreshToken);
    }
    @Transactional(noRollbackFor = RefreshTokenReuseException.class)
    public TokenResponse refreshTokenPair(String refreshToken)
    {
        Claims claims = jwtService.validateRefreshToken(refreshToken);
        String username = claims.getSubject();
        UserSession session = userSessionRep.findByRefreshTokenForUpdate(refreshToken).orElseThrow(() -> new InvalidRefreshToken("Refresh session not found"));
        if (session.getStatus() == SessionStatus.USED)
        {
            session.setStatus(SessionStatus.REVOKED);
            userSessionRep.save(session);
            throw new RefreshTokenReuseException("Refresh token reuse detected");
        }
        if (session.getStatus() == SessionStatus.REVOKED)
        {
            throw new InvalidRefreshToken("Refresh token was revoked");
        }
        if (session.getStatus() != SessionStatus.ACTIVE)
        {
            throw new InvalidRefreshToken("Invalid refresh session status");
        }
        if (!username.equals(session.getUsername()))
        {
            throw new InvalidRefreshToken("Session owner mismatch");
        }
        User user = userRep.findByEmail(username).orElseThrow(() -> new InvalidRefreshToken("User not found"));
        if (!user.isEnabled())
        {
            throw new InvalidRefreshToken("User is disabled");
        }
        if (!session.getRefreshTokenExpiry().isAfter(Instant.now()))
        {
            throw new InvalidRefreshToken("Refresh token expired");
        }
        session.setStatus(SessionStatus.USED);
        userSessionRep.save(session);

        String newAccessToken = jwtService.generateAccessToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);
        Claims newAccessClaims = jwtService.validateAccessToken(newAccessToken);
        Claims newRefreshClaims = jwtService.validateRefreshToken(newRefreshToken);
        UserSession newSession = new UserSession();
        newSession.setUsername(username);
        newSession.setDeviceId(session.getDeviceId());
        newSession.setAccessToken(newAccessToken);
        newSession.setRefreshToken(newRefreshToken);
        newSession.setAccessTokenExpiry(newAccessClaims.getExpiration().toInstant());
        newSession.setRefreshTokenExpiry(newRefreshClaims.getExpiration().toInstant());
        newSession.setStatus(SessionStatus.ACTIVE);
        userSessionRep.save(newSession);
        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
