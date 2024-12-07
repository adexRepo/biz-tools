package ecnic.service.user.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ecnic.service.common.models.BaseDTO;
import ecnic.service.common.models.CommonData;
import ecnic.service.common.security.UserRole;
import ecnic.service.user.domain.models.AddressInfo.AddressField;
import ecnic.service.user.domain.models.AddressInfo.AddressType;
import ecnic.service.user.domain.models.AddressInfo.CommonDataAddress;
import ecnic.service.user.domain.models.ContactInfo.CommonDataContact;
import ecnic.service.user.domain.models.ContactInfo.ContactField;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class CreateUserDTO extends BaseDTO {
    
    @NotNull
    private String username;
    
    @NotNull
    private String password;
    
    @NotNull
    private String nickName;
    
    @NotNull
    private String firstName;
    
    private String middleName;
    
    private String lastName;
    
    private List<String> deviceIds;
    
    @NotNull
    private List<UserRole> userRoles;
    
    private List<CommonDataAddress> addresses;
    
    private List<CommonDataContact> contacts;
    
    private List<String> emails;
    
    private UserStatus userStatus;
}
