package com.dinhhieu.FruitWebApp.model;

import com.dinhhieu.FruitWebApp.validator.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    @NotBlank(message = "firstName must be not empty")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "lastName must be not empty")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    @PhoneNumber(message = "phone invalid format")
    private String phoneNumber;

    private String googleId;

    private String authProvider;
//    @ElementCollection
//    @CollectionTable(name = "customer_roles", joinColumns = @JoinColumn(name = "customer_id"))
//    @Column(name = "role")
//    private Set<String> role;

    @ManyToMany
    private Set<Role> roles;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;

    @OneToMany(mappedBy = "customer")
    private List<Conversation> conversations;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    public void handleBeforeCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedAt = new Date();
    }



}
