package com.mikadev.finanzasfamiliares.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {

    private final String SECRET = "mySecretiux";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);


    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("role", userDetails.getAuthorities().toString())
                .withIssuedAt(new Date())
                .withExpiresAt((new Date(System.currentTimeMillis() + 3600000)))
                .sign(algorithm);
    }

    public DecodedJWT decodeToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
       return verifier.verify(token);
    }

    public boolean isTokenExpired(String token) {
        return decodeToken(token).getExpiresAt().before(new Date());
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return decodeToken(token).getSubject();
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        String role = decodeToken(token).getClaim("role").asString();
        role = role.replace("ROLE_", "");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

}
