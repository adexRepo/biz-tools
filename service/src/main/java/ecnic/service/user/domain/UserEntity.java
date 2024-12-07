package ecnic.service.user.domain;

import static ecnic.service.common.constants.DatabaseConstant.DB_SCHEMA_USER;

import ecnic.service.common.models.BaseEntity;
import ecnic.service.common.models.CommonData;
import ecnic.service.common.security.UserRole;
import ecnic.service.user.domain.models.AddressInfo.AddressField;
import ecnic.service.user.domain.models.AddressInfo.CommonDataAddress;
import ecnic.service.user.domain.models.ContactInfo;
import ecnic.service.user.domain.models.ContactInfo.CommonDataContact;
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
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(schema = DB_SCHEMA_USER, name = "t_mt_user")
@EqualsAndHashCode(callSuper = false)
class UserEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "salt_password", nullable = false)
    private String saltPassword;
    
    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "device_ids")
    private List<String> deviceIds;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "emails", columnDefinition = "jsonb")
    private List<String> email;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "addresses", columnDefinition = "jsonb")
    private List<CommonDataAddress> addresses;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "contacts", columnDefinition = "jsonb")
    private List<CommonDataContact> contacts;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "roles", columnDefinition = "jsonb")
    private List<UserRole> userRoles;
}
