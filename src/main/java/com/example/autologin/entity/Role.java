package com.example.autologin.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@Table(name = "\"role\"")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Role {

    @Id
    private String roleName;
    private String roleDescription;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated  = OffsetDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated  = OffsetDateTime.now();

}