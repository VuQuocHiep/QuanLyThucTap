package com.Hiep.B23DCCN295.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hiep.B23DCCN295.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity,String>{
    Optional<RoleEntity> findByName(String name);
    boolean existsByName(String name);
}
