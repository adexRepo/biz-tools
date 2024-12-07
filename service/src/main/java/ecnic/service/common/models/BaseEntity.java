package ecnic.service.common.models;


import ecnic.service.common.constants.BaseStatus.RecordStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.ZonedDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Data
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {
    
    /**
     * The Created by.
     */
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    protected String createdBy;
    
    /**
     * The Created dtm.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected ZonedDateTime createdDtm;
    
    /**
     * The Modified by.
     */
    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    protected String updatedBy;
    
    /**
     * The Modified dtm.
     */
    @LastModifiedDate
    @Column(name = "updated_at",nullable = false)
    protected ZonedDateTime updatedAt;
    
    /**
     * The Modified dtm.
     */
    @Column(name = "version", nullable = false)
    protected Long version;
    
    /**
     * The Status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected RecordStatus status;
    
    /**
     * On create.
     */
    @PrePersist
    protected void onCreate() {
        createdDtm = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
        status = RecordStatus.ACTIVE;
    }
    
    /**
     * On update.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
        version++;
    }
    
}
