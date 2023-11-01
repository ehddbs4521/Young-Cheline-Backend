package YoungCheline.YoungCheline.service;

import YoungCheline.YoungCheline.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.extract.internal.IndexInformationImpl;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtConfig jwtConfig;

    public String generateToken(String email) {
        Date now= new Date();
        String compact = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 365)))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getKey())
                .compact();
        log.info("compact={}", compact);
        return compact;
    }

    public String validateToken(String token) {
        SecretKeySpec key = new SecretKeySpec(jwtConfig.getKey().getBytes(), "HS512");
        try {
            Claims claim = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claim.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

}
