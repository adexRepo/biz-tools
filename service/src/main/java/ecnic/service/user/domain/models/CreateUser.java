package ecnic.service.user.domain.models;

import ecnic.service.common.models.RoleType;
import java.util.List;

public record CreateUser(
    String username,
    String firstname,
    String middleName,
    String lastName,
    RoleType role,
    List<String> address,
    List<String> phoneNumber,
    List<String> email,
    Long createdBy
) {

}
