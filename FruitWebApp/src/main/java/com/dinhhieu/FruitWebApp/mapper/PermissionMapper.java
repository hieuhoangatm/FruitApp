package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.PermissionRequest;
import com.dinhhieu.FruitWebApp.dto.response.PermissionResponse;
import com.dinhhieu.FruitWebApp.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissonRequest);

    PermissionResponse toPermissionResponse(Permission permission);
}
