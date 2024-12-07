package ecnic.service.common.security.limiter;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;

public class RateLimiterAuthenticationProviderProcessor<T extends AuthenticationProvider> implements ObjectPostProcessor<T> {
    private final Class<T> clazz;

    public RateLimiterAuthenticationProviderProcessor(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public <V extends T> V postProcess(V object) {
        if (clazz.isAssignableFrom(object.getClass())) {
            return (V) new RateLimitedAuthenticationProvider(object);
        }
        return object;
    }
}
