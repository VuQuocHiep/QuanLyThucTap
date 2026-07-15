package com.Hiep.B23DCCN295.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hiep.B23DCCN295.dto.request.PermissionRequest;
import com.Hiep.B23DCCN295.entity.PermissionEntity;
import com.Hiep.B23DCCN295.service.PermissionService;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/createPermission")
    public ResponseEntity<PermissionEntity> createPermission(@Valid @RequestBody PermissionRequest request){
        return ResponseEntity.ok(permissionService.createPermission(request));
    }

    @GetMapping("/getAllPermission")
    public ResponseEntity<List<PermissionEntity>> getAllPermission(){
        return ResponseEntity.ok(permissionService.getAllPermission());
    }

    @DeleteMapping("/deletePermission/{name}")
    public void deletePermission(@PathVariable String name){
        permissionService.deletePermission(name);
    }

    @GetMapping("/getPermissionByName/{name}")
    public ResponseEntity<PermissionEntity> getPermissionByName(@PathVariable String name){
        return ResponseEntity.ok(permissionService.getPermissionByName(name));
    }

    @PatchMapping("/updatePermission/{name}")
    public ResponseEntity<PermissionEntity> updatePermission(@Valid @RequestBody PermissionRequest request,@PathVariable String name){
        return ResponseEntity.ok(permissionService.updatePermission(request, name));
    }
}
