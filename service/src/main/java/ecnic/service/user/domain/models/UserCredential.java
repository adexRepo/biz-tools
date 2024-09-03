package ecnic.service.user.domain.models;

import java.util.Set;

public record UserCredential(
        String username,
        String firstName,
        String lastName,
        String password,
        String token,
        Set<UserRole> roles
) {

}
