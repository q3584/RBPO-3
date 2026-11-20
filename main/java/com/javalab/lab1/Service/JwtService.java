package com.javalab.lab1.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service 
public class JwtService {
    private final SecretKey secretKey;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.access-expiration}") long accessExpiration, @Value("${jwt.refresh-expiration}") long refreshExpiration)
    {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration; 
    }
    public String generateAccessToken(String username)
    {
        return generateToken(username, "access", accessExpiration);
    }
    public String generateRefreshToken(String username)
    {
        return generateToken(username, "refresh", refreshExpiration);
    }
    public String extractEmail(String token)
    {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
    private String generateToken(String username, String type, long expiration)
    {
        Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiry = now.plusSeconds(expiration);

        return Jwts.builder()
                .subject(username)
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .claim("type", type)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public Claims validateAccessToken(String token)
    {
        return validateToken(token, "access");
    }
    public Claims validateRefreshToken(String token)
    {
        return validateToken(token, "refresh");
    }

    private Claims validateToken(String token, String expectedType)
    {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        if (claims.getSubject() == null || claims.getSubject().isBlank())
        {
            throw new JwtException("Jwt subject is missing");
        }
        if (claims.getId() == null || claims.getId().isBlank())
        {
            throw new JwtException("Jwt jti is missing");
        }
        if (claims.getIssuedAt() == null)
        {
            throw new JwtException("Jwt iat is missing");
        }
        if (claims.getExpiration() == null)
        {
            throw new JwtException("Jwt exp is missing");
        }
        String actualType = claims.get("type", String.class);

        if (!expectedType.equals(actualType))
        {
            throw new JwtException("Invalid JWT type");
        }
        return claims;
    }
}
