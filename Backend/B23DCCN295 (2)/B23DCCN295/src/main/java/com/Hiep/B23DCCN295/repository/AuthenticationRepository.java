package com.Hiep.B23DCCN295.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hiep.B23DCCN295.entity.UserEntity;

public interface AuthenticationRepository extends JpaRepository<UserEntity,String>{
    
    UserEntity findByEmail(String email);

}
