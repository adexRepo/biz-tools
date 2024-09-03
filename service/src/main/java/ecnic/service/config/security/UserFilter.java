package ecnic.service.config.security;

import ecnic.service.config.security.jwt.JwtService;
import ecnic.service.config.security.jwt.JwtUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class UserFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // CORS
        response.addHeader("Access-Control-Allow-Origin", "*");
        
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.addHeader("Access-Control-Allow-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            response.addHeader("Access-Control-Max-Age", "1");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        }

        var token = recoverToken(request);
        if (token != null && token.startsWith("Bearer ")) {
            var login = jwtService.validateToken(token);
            if (login != null) {
                var user = jwtUserDetailsService.loadUserByUsername(login);
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
            return;
        }

        if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
