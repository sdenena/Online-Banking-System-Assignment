package com.sd.onlinebankingsystemassignment.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Column(columnDefinition = "boolean default true")
    private Boolean status = true;

    @Column(name = "version")
    @Version
    private int version;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate;

    @JsonIgnoreProperties(allowSetters = true)
    // @JsonIgnore
    @Column(name = "created_by_id")
    @CreatedBy
    private Long createById = null;

    @JsonIgnoreProperties(allowSetters = true)
    // @JsonIgnore
    @Column(name = "updated_by_id")
    @LastModifiedBy
    private Long updateById = null;

    /**
     *
     */
    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    /**
     *
     */
    @PreUpdate
    protected void onUpdate() {
        updatedDate = new  Date();
    }
}
