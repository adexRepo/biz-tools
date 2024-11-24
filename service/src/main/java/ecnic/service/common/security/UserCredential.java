package ecnic.service.common.security;

import java.util.Set;

/**
 * The type User credential.
 */
public record UserCredential(
        String username,
        String firstName,
        String lastName,
        String password,
        String token,
        Set<UserRole> roles
) {

}
