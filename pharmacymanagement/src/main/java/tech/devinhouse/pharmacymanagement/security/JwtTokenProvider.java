package tech.devinhouse.pharmacymanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String secret = "SENHA_SEGURANCA";

    private static final long expiration = 24 * 60 * 60 * 2 * 1000;

    public String generateToken(String username) {
        Date expire = new Date(new Date().getTime() + expiration);

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return token;
    }

    public String getUsernameJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validadeToken(String token) {
        Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);

        return true;
    }
}
