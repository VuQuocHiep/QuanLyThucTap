package com.Hiep.B23DCCN295.mapper;

import com.Hiep.B23DCCN295.dto.request.RoleRequest;
import com.Hiep.B23DCCN295.entity.RoleEntity;

public class RoleMapper {
    
    public static RoleEntity toEntity(RoleRequest request){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(request.getName());
        roleEntity.setDescription(request.getDescription());
        return roleEntity;
    }

}
