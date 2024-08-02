package com.dinhhieu.FruitWebApp.controller;

import com.dinhhieu.FruitWebApp.dto.ResponData;
import com.dinhhieu.FruitWebApp.dto.request.PermissionRequest;
import com.dinhhieu.FruitWebApp.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping("")
    public ResponData<?> createPermiss(@RequestBody PermissionRequest permissonRequest){
        return new ResponData<>(HttpStatus.OK.value(), "create permission", this.permissionService.create(permissonRequest));
    }

    @GetMapping("")
    public ResponData<?> getAllPermiss(){
        return new ResponData<>(HttpStatus.OK.value(), "get all permission", this.permissionService.getAll());
    }

    @DeleteMapping("{id}")
    public ResponData<?> delelePermiss(@PathVariable("id") String id){
        this.permissionService.delete(id);
        return new ResponData<>(HttpStatus.NO_CONTENT.value(), "deleted permission successfully");
    }
}
