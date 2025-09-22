package com.SmartLaundry.laundry.Service.JWT;

import com.SmartLaundry.laundry.Entity.User.User;
import com.SmartLaundry.laundry.Repository.User.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import java.util.function.Function;

@Service
public class JWTService {

    private final UserRepository repository;
    private final String secretKey;

    @Value("${jwt.expiration}")
    private long exp_millis;
    public JWTService(UserRepository repository){
        this.repository = repository;
        String envKey = System.getenv("JWT_SECRET_BASE64");
        if (envKey != null && !envKey.isBlank()) {
            this.secretKey = envKey;
        } else {
            this.secretKey = generateBase64Key(); // dev fallback
        }
    }

    private String generateBase64Key() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String genarateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();

        repository.findByEmail(userName).ifPresent(u -> {
            claims.put("role", u.getRole() != null ? u.getRole().name() : "CUSTOMER");
            claims.put("email", u.getEmail());
            claims.put("name", u.getName());
        });

        Date now = new Date();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + exp_millis))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] convertKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(convertKey);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validatToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}