package ecnic.service.common.utilities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemDateUtil {
    
    @Value("${spring.jackson.time-zone}")
    private String timezone;
    
    public ZonedDateTime convertToSystemZonedTime(LocalDateTime localDateTime, ZoneId zoneId) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        ZoneId zoneIdSystem = ZoneId.of(timezone);
        return instant.atZone(zoneIdSystem);
    }
    
    public ZonedDateTime convertEpochToSystemZonedDateTime(Long epochMilli){
        Instant instant = Instant.ofEpochMilli(epochMilli);
        ZoneId zoneIdSystem = ZoneId.of(timezone);
        return instant.atZone(zoneIdSystem);
    }
    
}
