package ecnic.service.user.domain.models;

import java.util.List;

public record User(
    Long id,
    String firstName,
    String middleName,
    String lastName,
    List<String> address,
    List<String> phoneNumber,
    List<String> email
) {
}
