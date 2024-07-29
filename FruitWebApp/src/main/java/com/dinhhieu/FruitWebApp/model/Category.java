package com.dinhhieu.FruitWebApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "category_name")
    @NotBlank(message = "categoryName must be not empty")
    private String categoryName;

    @Column(name = "url_image")
    private String urlImage;

    @Column(name = "description_image")
    private String descriptionImage;

    @Column(name = "type")
    private String type;

    private Date createdAt;
    private Date updatedAt;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "category")
    private List<Product> product;

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
