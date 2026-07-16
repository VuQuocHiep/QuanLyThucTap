package com.Hiep.B23DCCN295.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hiep.B23DCCN295.dto.request.UserRequest;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserEntity>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }
    
    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PatchMapping("/updateUser/{email}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String email,@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.updateUser(email, request));
    }

    @PostMapping("/deleteUser/{email}")
    public void deleteUser(@PathVariable String email){
        userService.deleteUser(email);
    }
}
