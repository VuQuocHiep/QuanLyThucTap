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

import com.Hiep.B23DCCN295.dto.request.RoleRequest;
import com.Hiep.B23DCCN295.entity.RoleEntity;
import com.Hiep.B23DCCN295.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    @PostMapping("/createRole")
    public ResponseEntity<RoleEntity> createRole(@RequestBody RoleRequest request){
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @GetMapping("/getAllRole")
    public ResponseEntity<List<RoleEntity>> getAllRole(){
        return ResponseEntity.ok(roleService.getAllRole());
    }

    @GetMapping("/getRoleByName/{name}")
    public ResponseEntity<RoleEntity> getRoleByName(@PathVariable String name){
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    @DeleteMapping("/deleteRole/{name}")
    public void deleteRole(@PathVariable String name){
        roleService.deleteRole(name);
    }

    @PatchMapping("/updateRole/{name}")
    public ResponseEntity<RoleEntity> updateRole(@Valid @RequestBody RoleRequest request,@PathVariable String name){
        return ResponseEntity.ok(roleService.updateRole(request, name));
    }
}
