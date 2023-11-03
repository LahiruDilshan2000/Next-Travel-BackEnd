package lk.ijse.apigateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

/**
 * @author Lahiru Dilshan
 * @created Sat 10:57 AM on 10/21/2023
 * @project identity-server
 **/
@Service
public class JwtUtil {

    @Value("${secret-key}")
    private String SECRET_KEY;

    public void validateToken(final String token){

        Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token);
    }

    private Key getSignInKey() {

        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
