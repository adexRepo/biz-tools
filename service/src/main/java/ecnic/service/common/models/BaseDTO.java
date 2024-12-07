package ecnic.service.common.models;


import ecnic.service.common.constants.BaseStatus.RecordStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

public class BaseDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 2611653907960515145L;
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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    protected ZonedDateTime createdDtm;
    
    /**
     * The Modified by.
     */
    @LastModifiedBy
    protected String modifiedBy;
    
    /**
     * The Modified dtm.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
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
    protected RecordStatus status;
}
