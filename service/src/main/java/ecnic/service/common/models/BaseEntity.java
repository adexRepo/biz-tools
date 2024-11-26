package ecnic.service.common.models;


import ecnic.service.common.constants.BaseStatus.OperationalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * The type Base entity.
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {
    
    /**
     * The Created by.
     */
    @CreatedBy
    @Column(nullable = false, updatable = false)
    protected String createdBy;
    
    /**
     * The Created dtm.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected ZonedDateTime createdDtm;
    
    /**
     * The Modified by.
     */
    @LastModifiedBy
    @Column(nullable = false)
    protected String modifiedBy;
    
    /**
     * The Modified dtm.
     */
    @LastModifiedDate
    @Column(nullable = false)
    protected ZonedDateTime modifiedDtm;
    
    /**
     * The Modified dtm.
     */
    @Column(nullable = false)
    protected Long version = 0L;
    
    /**
     * The Status.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected OperationalStatus status;
    
    /**
     * On create.
     */
    @PrePersist
    protected void onCreate() {
        createdDtm = ZonedDateTime.now();
        modifiedDtm = ZonedDateTime.now();
        status = OperationalStatus.ACTIVE;
    }
    
    /**
     * On update.
     */
    @PreUpdate
    protected void onUpdate() {
        modifiedDtm = ZonedDateTime.now();
        version++;
    }
    
}
