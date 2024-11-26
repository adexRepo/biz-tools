package ecnic.service.user.domain.models;

import ecnic.service.common.models.BaseDTO;
import ecnic.service.common.security.UserRole;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateUserDTO extends BaseDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    private String middleName;
    private String lastName;
    private List<String> deviceIds;
    @NotNull
    private List<UserRole> userRoles;
    private List<String> addresses;
    private List<String> phoneNumbers;
    private List<String> emails;
    private UserStatus userStatus;
}
