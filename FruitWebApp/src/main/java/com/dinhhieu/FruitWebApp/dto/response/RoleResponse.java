package com.dinhhieu.FruitWebApp.dto.response;

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
public class RoleResponse {
    private String name;

    private String description;

    private Set<PermissionResponse> permissions;
}
