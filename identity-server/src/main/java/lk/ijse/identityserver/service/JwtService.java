package lk.ijse.identityserver.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lk.ijse.identityserver.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:57 AM on 10/21/2023
 * @project identity-server
 **/
@Service
public class JwtService {

    @Value("${secret-key}")
    private String SECRET_KEY;

    public String generateToken(String userName, Role role) {
        return generateToken(new HashMap<>(), userName, role);
    }

    public String generateToken(Map<String, Objects> claims, String userName, Role role) {

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userName)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(final String token){
        Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
    }

    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
