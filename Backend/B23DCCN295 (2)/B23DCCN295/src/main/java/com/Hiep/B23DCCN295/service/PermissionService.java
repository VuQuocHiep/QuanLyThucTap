package com.Hiep.B23DCCN295.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.PermissionRequest;
import com.Hiep.B23DCCN295.entity.PermissionEntity;
import com.Hiep.B23DCCN295.mapper.PermissionMapper;
import com.Hiep.B23DCCN295.repository.PermissionRepository;

@Service
public class PermissionService {
    
    @Autowired
    private PermissionRepository permissionRepository;

    public PermissionEntity createPermission(PermissionRequest request){
        PermissionEntity permissionEntity = PermissionMapper.toEntity(request);
        if(!permissionRepository.findByName(permissionEntity.getName()).isEmpty()){
            throw new RuntimeException("It already existed");
        }
        return permissionRepository.save(permissionEntity);
    }

    public List<PermissionEntity> getAllPermission(){
        return permissionRepository.findAll();
    }

    public void deletePermission(String name){    
        PermissionEntity permissionEntity = permissionRepository.findByName(name).orElseThrow(()->new RuntimeException("Not found!"));
        permissionRepository.delete(permissionEntity);
    }

    public PermissionEntity getPermissionByName(String name){
        PermissionEntity permissionEntity = permissionRepository.findByName(name).orElseThrow(()->new RuntimeException("Not found!"));
        return permissionEntity;
    }

    public PermissionEntity updatePermission(PermissionRequest request,String name){
        if(!permissionRepository.existsByName(name)){
            throw new RuntimeException("Not found!");
        }
        PermissionEntity permissionEntity = PermissionMapper.toEntity(request);
        return permissionRepository.save(permissionEntity);
    }

}
