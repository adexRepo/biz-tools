package ecnic.service.user.domain;

import static ecnic.service.common.constants.DatabaseConstant.USER;

import ecnic.service.common.models.BaseEntity;
import ecnic.service.common.constants.BaseStatus;
import ecnic.service.user.domain.models.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(schema = USER, name = "t_user")
@EqualsAndHashCode(callSuper = false)
class UserEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String saltPassword;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<String> deviceIds;
    private List<String> email;
    private List<String> address;
    private List<String> phoneNumber;
    @Enumerated(EnumType.STRING)
    private BaseStatus status;
    
    private boolean isLogin;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<UserRole> userRoles;
}
