package ecnic.service.common.security;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import ecnic.service.common.security.handler.ForbiddenHandler;
import ecnic.service.common.security.handler.UnauthorizedHandler;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * The type Security configuration.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    
    private final UnauthorizedHandler unauthorizedHandler;
    private final ForbiddenHandler forbiddenHandler;
    private final UserFilter userFilter;
    
    /**
     * Security filter chain security filter chain.
     *
     * @param http      the http
     * @param publisher the publisher
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            AuthenticationEventPublisher publisher) throws Exception {
        http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationEventPublisher(publisher);
        
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(handler -> {
                    handler.accessDeniedHandler(forbiddenHandler); // when role not match
                    handler.authenticationEntryPoint(unauthorizedHandler); // when not login
                })
                .authorizeHttpRequests(authorizeConfig -> {
                    authorizeConfig.requestMatchers(HttpMethod.GET, "/api/users/test1").permitAll();
                    authorizeConfig.requestMatchers(HttpMethod.POST, "/api/v1/users/register")
                            .permitAll();
                    authorizeConfig.requestMatchers("/upload.html").permitAll();
                    authorizeConfig.requestMatchers("/swagger-ui.html/**").permitAll();
                    authorizeConfig.anyRequest().authenticated();
                })
                .cors(crs -> crs.configurationSource(corsConfigurationSource()))
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(userFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    
    @Bean
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOrigins(List.of("http://localhost:88291"));
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(
                Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(
                Arrays.asList("Authorization", "Channel-Type", "Content-Type"));
        configuration.setExposedHeaders(List.of("X-Get-Header"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    /**
     * Auth success application listener.
     *
     * @return the application listener
     */
    @Bean
    ApplicationListener<AuthenticationSuccessEvent> authSuccess() {
        return event -> {
            var auth = event.getAuthentication();
            LoggerFactory.getLogger(SecurityConfiguration.class)
                    .info("Login Successful [{}] - {}", auth.getClass().getSimpleName(),
                            auth.getName());
        };
    }
    
}
