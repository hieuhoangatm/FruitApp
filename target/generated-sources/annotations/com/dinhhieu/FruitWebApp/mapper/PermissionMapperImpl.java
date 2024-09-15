package com.dinhhieu.FruitWebApp.mapper;

import com.dinhhieu.FruitWebApp.dto.request.PermissionRequest;
import com.dinhhieu.FruitWebApp.dto.response.PermissionResponse;
import com.dinhhieu.FruitWebApp.model.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-27T11:23:43+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toPermission(PermissionRequest permissonRequest) {
        if ( permissonRequest == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.name( permissonRequest.getName() );
        permission.description( permissonRequest.getDescription() );

        return permission.build();
    }

    @Override
    public PermissionResponse toPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.name( permission.getName() );
        permissionResponse.description( permission.getDescription() );

        return permissionResponse.build();
    }
}
