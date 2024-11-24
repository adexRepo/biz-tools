package ecnic.service.common.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Jwt service.
 */
@Component
public class JwtService {
    
    @Value("${api.security.token.secret}")
    private String secret;
    
    private static final String ISSUER = "auth-api";
    
    /**
     * Generate token string.
     *
     * @param username the auth credential
     * @return the string
     */
    public String generateToken(String username) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(username)
                .withKeyId(UUID.randomUUID().toString())
                .withExpiresAt(this.calculateExpiration())
                .sign(this.getAlgorithm());
    }
    
    /**
     * Validate token string.
     *
     * @param token the token
     * @return the string
     */
    public String validateToken(String token) {
        return JWT.require(this.getAlgorithm())
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }
    
    private Instant calculateExpiration() {
        final int EXPIRATION_HOURS = 2;
        return LocalDateTime.now()
                .plusHours(EXPIRATION_HOURS)
                .toInstant(this.systemOffset());
    }
    
    private ZoneOffset systemOffset() {
        ZonedDateTime zdt = ZonedDateTime.now();
        ZoneId zoneId = zdt.getZone();
        int offsetHours = zoneId.getRules().getOffset(zdt.toInstant()).getTotalSeconds() / 3600;
        return ZoneOffset.ofHours(offsetHours);
    }
    
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.secret);
    }
}
