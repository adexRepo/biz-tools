package ecnic.service.user.domain.models;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateUser(
        String username,
        @NotNull String password,
        @NotNull String firstName,
        String middleName,
        String lastName,
        @NotNull List<String> deviceIds,
        @NotNull List<UserRole> userRoles,
        List<String> addresses,
        List<String> phoneNumbers,
        List<String> emails,
        @NotNull String createdBy
) {

}
