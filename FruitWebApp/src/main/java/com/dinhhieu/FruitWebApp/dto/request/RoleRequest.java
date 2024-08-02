package com.dinhhieu.FruitWebApp.dto.request;

import com.dinhhieu.FruitWebApp.model.Permission;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private String name;

    private String description;

    Set<String> permissions;
}
