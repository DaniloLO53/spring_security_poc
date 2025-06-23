package org.ecommerce.spring_security_poc.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static Long jwtExpirationMs = 3000000L;
    private static String jwtSecret = "mySecretKey123912738aopsgjnspkmndfsopkvajoirjg94gf2opfng2moknm";

    public String generateTokenFromUserName(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(generateSigninKey())
                .compact();
    }

    private Key generateSigninKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
