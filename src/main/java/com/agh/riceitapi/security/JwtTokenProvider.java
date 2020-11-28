package com.agh.riceitapi.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private String jwtExpirationInMs;

    public String generateToken(Authentication auth){

        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + Long.parseLong(jwtExpirationInMs)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch(SignatureException ex){
            logger.error("Invalid JWT signature");
        } catch(MalformedJwtException ex){
            logger.error("Invalid JWT token");
        } catch(ExpiredJwtException ex){
            logger.error("Expired JWT signature");
        } catch(UnsupportedJwtException  ex){
            logger.error("Unsupported JWT token");
        } catch(IllegalArgumentException  ex){
            logger.error("JWT claims string is empty");
        }
        return false;
    }
}
