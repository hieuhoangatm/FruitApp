package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.dto.request.RoleRequest;
import com.dinhhieu.FruitWebApp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("")
    public ResponData<?> createRole(@RequestBody RoleRequest roleRequest){
        return new ResponData<>(HttpStatus.OK.value(), "create role", this.roleService.create(roleRequest));
    }

    @GetMapping("")
    public ResponData<?> getAll(){
        return new ResponData<>(HttpStatus.OK.value(), "get all data", this.roleService.getAll());
    }

    @DeleteMapping("{id}")
    public ResponData<?> deleteRole(@PathVariable("id") String id){
        this.roleService.delete(id);
        return new ResponData<>(HttpStatus.NO_CONTENT.value(), "deleted role with id "+ id);
    }
}
