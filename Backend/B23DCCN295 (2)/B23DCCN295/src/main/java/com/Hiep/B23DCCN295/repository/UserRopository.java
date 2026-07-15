package com.Hiep.B23DCCN295.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hiep.B23DCCN295.entity.UserEntity;

public interface UserRopository extends JpaRepository<UserEntity,String>{
    
    Optional<UserEntity> findByRole_Name(String role);
    
    Optional<UserEntity> findByEmail(String email);
}
