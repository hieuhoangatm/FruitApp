package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.dto.request.PermissionRequest;
import com.dinhhieu.FruitWebApp.dto.response.PermissionResponse;
import com.dinhhieu.FruitWebApp.mapper.PermissionMapper;
import com.dinhhieu.FruitWebApp.model.Permission;
import com.dinhhieu.FruitWebApp.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PermissionService {
    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;
    public PermissionResponse create(PermissionRequest permissonRequest){
        Permission permission =  this.permissionMapper.toPermission(permissonRequest);
        this.permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll(){
        List<Permission> permissions = this.permissionRepository.findAll();
        return permissions.stream().map(this.permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String id){
        this.permissionRepository.deleteById(id);
    }

}
