package com.Hiep.B23DCCN295.mapper;

import com.Hiep.B23DCCN295.dto.request.PermissionRequest;
import com.Hiep.B23DCCN295.entity.PermissionEntity;

public class PermissionMapper {
    
    public static PermissionEntity toEntity(PermissionRequest request){
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setName(request.getName());
        permissionEntity.setDescription(request.getDescription());
        return permissionEntity;
    }

}
