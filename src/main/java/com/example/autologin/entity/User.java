package com.example.autologin.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String phoneNumber;

    @Column
    private String adress;

    @Column
    private String region;

    @Column
    private String city;

    @Column
    private Long zip;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> roles;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated  = OffsetDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated  = OffsetDateTime.now();

}
