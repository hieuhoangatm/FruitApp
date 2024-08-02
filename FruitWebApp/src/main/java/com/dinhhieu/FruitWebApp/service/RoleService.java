package com.dinhhieu.FruitWebApp.service;

import com.dinhhieu.FruitWebApp.dto.request.RoleRequest;
import com.dinhhieu.FruitWebApp.dto.response.RoleResponse;
import com.dinhhieu.FruitWebApp.mapper.RoleMapper;
import com.dinhhieu.FruitWebApp.model.Permission;
import com.dinhhieu.FruitWebApp.model.Role;
import com.dinhhieu.FruitWebApp.repository.PermissionRepository;
import com.dinhhieu.FruitWebApp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final RoleMapper roleMapper;
    public RoleResponse create(RoleRequest request){
        Role role = this.roleMapper.toRole(request);

        var permission = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permission));

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }


    public List<RoleResponse> getAll(){
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role){
        this.roleRepository.deleteById(role);
    }

}
