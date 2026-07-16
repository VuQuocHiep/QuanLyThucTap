package com.Hiep.B23DCCN295.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.RoleRequest;
import com.Hiep.B23DCCN295.entity.PermissionEntity;
import com.Hiep.B23DCCN295.entity.RoleEntity;
import com.Hiep.B23DCCN295.mapper.RoleMapper;
import com.Hiep.B23DCCN295.repository.PermissionRepository;
import com.Hiep.B23DCCN295.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public RoleEntity createRole(RoleRequest request){
        if(roleRepository.existsByName(request.getName())){
            throw new RuntimeException("Đã tồn tại");
        }
        Set<PermissionEntity> permission = new HashSet<>();
        RoleEntity roleEntity = RoleMapper.toEntity(request);
        for(String x:request.getPermission()){
            PermissionEntity s = permissionRepository.findByName(x).orElseThrow(()->new RuntimeException("NOT FOUND!"));
            permission.add(s);
        }
        roleEntity.setPermission(permission);
        return roleRepository.save(roleEntity);
    }

    public List<RoleEntity> getAllRole(){
        return roleRepository.findAll();
    }

    public RoleEntity getRoleByName(String name){
        RoleEntity roleEntity = roleRepository.findByName(name).orElseThrow(()->new RuntimeException("NOT FOUND!"));
        return roleEntity;
    }

    public void deleteRole(String name){
        RoleEntity roleEntity = roleRepository.findByName(name).orElseThrow(()->new RuntimeException("NOT FOUND!"));
        roleRepository.delete(roleEntity);
    }

    public RoleEntity updateRole(RoleRequest request,String name){
        if(!roleRepository.existsByName(name)){
            throw new RuntimeException("Not Found!");
        }
        RoleEntity roleEntity = RoleMapper.toEntity(request);
        return roleRepository.save(roleEntity);
    }

}
