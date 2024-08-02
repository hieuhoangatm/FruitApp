package com.dinhhieu.FruitWebApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "groups")
public class Group extends AbstractEntity<Integer>{
    private String name;

    private String description;

}
