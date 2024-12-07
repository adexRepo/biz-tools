package ecnic.service.user.domain.models;

import ecnic.service.user.domain.models.AddressInfo.CommonDataAddress;
import ecnic.service.user.domain.models.ContactInfo.CommonDataContact;
import java.util.List;
import java.util.UUID;

public record User(
        UUID id,
        String firstName,
        String middleName,
        String lastName,
        List<CommonDataAddress> addresses,
        List<CommonDataContact> contacts,
        List<String> emails,
        UserStatus userStatus
) {

}
