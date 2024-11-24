package ecnic.service.user.domain.models;

import java.util.List;
import java.util.UUID;

public record User(
    UUID id,
    String firstName,
    String middleName,
    String lastName,
    List<String> address,
    List<String> phoneNumber,
    List<String> email
) {
}
