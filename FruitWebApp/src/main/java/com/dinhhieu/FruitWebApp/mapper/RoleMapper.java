package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.RoleRequest;
import com.dinhhieu.FruitWebApp.dto.response.RoleResponse;
import com.dinhhieu.FruitWebApp.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true
    )
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
