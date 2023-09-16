package in.watch.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    private final static String SECRET_KEY = "OPBgeYn8Dx8pfcKbak0IHZvxRJGBn0mulkfafhsiklghisfjopsfjopjpsfjskfj";

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);//subject is username
    }

    //extracting one claim
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extreactAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extreactAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    //this method returns Key that is used to sign jwt token
    private Key getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //to generate token
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())// for spring its username but here its email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))//valid for 24hr +1000ms
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    //to generate without extraClaims
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    //to validate a token
    public boolean isTokenValid(String token, UserDetails userDetails) {

        String username = extractUsername(token);
        //username==>email in our case because we are returning email in user class
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
