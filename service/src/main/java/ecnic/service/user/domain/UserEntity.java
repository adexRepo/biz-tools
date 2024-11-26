package ecnic.service.user.domain;

import static ecnic.service.common.constants.DatabaseConstant.DB_SCHEMA_USER;

import ecnic.service.common.models.BaseEntity;
import ecnic.service.common.security.UserRole;
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
import java.util.Locale;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(schema = DB_SCHEMA_USER, name = "t_user")
@EqualsAndHashCode(callSuper = false)
class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String saltPassword;
    private String firstName;
    private String middleName;
    private String lastName;
    private String lastToken;
    private List<String> deviceIds;
    private List<String> email;
    private List<String> address;
    private List<String> phoneNumber;
    private Locale locale;
    private boolean isLogin;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<UserRole> userRoles;
}
