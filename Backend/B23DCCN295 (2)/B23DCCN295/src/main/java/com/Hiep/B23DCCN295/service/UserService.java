package com.Hiep.B23DCCN295.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.UserRequest;
import com.Hiep.B23DCCN295.entity.RoleEntity;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.mapper.UserMapper;
import com.Hiep.B23DCCN295.repository.RoleRepository;
import com.Hiep.B23DCCN295.repository.UserRopository;

@Service
public class UserService {
    
    @Autowired
    private UserRopository userRopository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    public UserEntity createUser(UserRequest request){
        if(userRopository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Đã tồn tại");
        }
        UserEntity userEntity = userMapper.toEntity(request);
        return userRopository.save(userEntity);
    }

    public List<UserEntity> getAllUser(){
        return userRopository.findAll();
    }

    public UserEntity getUserByEmail(String email){
        if(!userRopository.existsByEmail(email)){
            throw new RuntimeException("Not found!");
        }
        UserEntity userEntity = userRopository.findByEmail(email);
        return userEntity;
    }

    public void deleteUser(String email){
        if(!userRopository.existsByEmail(email)){
            throw new RuntimeException("Not found!");
        }
        UserEntity userEntity = userRopository.findByEmail(email);
        userEntity.setDeleted(true);
        userRopository.save(userEntity);
    }

    public UserEntity updateUser(String email,UserRequest request){
        if(!userRopository.existsByEmail(email)){
            throw new RuntimeException("Not found!");
        }
        UserEntity userEntity = userMapper.toEntity(request);
        return userRopository.save(userEntity);
    }
}
