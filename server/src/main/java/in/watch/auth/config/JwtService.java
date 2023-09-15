package in.watch.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    public String extractUsername(String token) {

        return null;
    }

    private Claims extreactAllClaims(String token) {

        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    //this method returns Key that is used to sign jwt token
    private Key getSignInKey() {
    }
}
