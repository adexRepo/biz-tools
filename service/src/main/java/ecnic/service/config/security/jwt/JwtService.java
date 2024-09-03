package ecnic.service.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ecnic.service.user.domain.models.UserCredential;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "auth-api";

    public String generateToken(UserCredential authCredential) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(authCredential.username())
                .withExpiresAt(this.calculateExpiration())
                .sign(this.getAlgorithm());
    }

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
    
    private ZoneOffset systemOffset(){
        ZonedDateTime zdt = ZonedDateTime.now();
        ZoneId zoneId = zdt.getZone();
        int offsetHours = zoneId.getRules().getOffset(zdt.toInstant()).getTotalSeconds() / 3600;
        return ZoneOffset.ofHours(offsetHours);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.secret);
    }
}
