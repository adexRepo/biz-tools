package ecnic.service.common.security.jwt;

import ecnic.service.common.security.UserCredential;
import ecnic.service.user.UserService;
import ecnic.service.common.security.UserRole;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * The type Jwt user details service.
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {
    
    private final UserService userService;
    
    /**
     * Instantiates a new Jwt user details service.
     *
     * @param userService the user service
     */
    public JwtUserDetailsService(@Autowired UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential user = userService.findUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(
                    String.format("Username %s not found!", username));
        }
        // future need to add if user login cannot "login" other app
        
        return new User(username, user.password(), mappingAuthorities(user.roles()));
    }
    
    private Collection<? extends GrantedAuthority> mappingAuthorities(
            Set<UserRole> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
    }
    
    
}
