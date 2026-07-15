package com.Hiep.B23DCCN295.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hiep.B23DCCN295.entity.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity,String>{

    Optional <PermissionEntity> findByName(String name);

    boolean existsByName(String name);
}
