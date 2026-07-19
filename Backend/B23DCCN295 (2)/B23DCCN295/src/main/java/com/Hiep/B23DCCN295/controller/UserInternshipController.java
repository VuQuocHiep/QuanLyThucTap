package com.Hiep.B23DCCN295.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hiep.B23DCCN295.dto.request.UserInternshipRequest;
import com.Hiep.B23DCCN295.entity.UserInternshipEntity;
import com.Hiep.B23DCCN295.service.UserInternshipService;

@RestController
@RequestMapping("/userInternship")
public class UserInternshipController {
    @Autowired
    private UserInternshipService userInternshipService;
    @PostMapping("/createUserInternship")
    public ResponseEntity<UserInternshipEntity> createUserInternship(@Valid @RequestBody UserInternshipRequest request){
        return ResponseEntity.ok(userInternshipService.createUserInternship(request));
    }
    @GetMapping("/getAllUserInternship")
    public ResponseEntity<List<UserInternshipEntity>> getAllUserInternship(){
        return ResponseEntity.ok(userInternshipService.getAllUserInternship());
    }
    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<List<UserInternshipEntity>> getByUser(@PathVariable String userId){
        return ResponseEntity.ok(userInternshipService.getByUser(userId));
    }
    @GetMapping("/getByInternship/{internshipId}")
    public ResponseEntity<List<UserInternshipEntity>> getByInternship(@PathVariable String internshipId){
        return ResponseEntity.ok(userInternshipService.getByInternship(internshipId));
    }
    @GetMapping("/getStudentByInternship/{internshipId}")
    public ResponseEntity<List<UserInternshipEntity>> getStudentByInternship(@PathVariable String internshipId){
        return ResponseEntity.ok(userInternshipService.getStudentByInternship(internshipId));
    }
    @GetMapping("/getLecturerByInternship/{internshipId}")
    public ResponseEntity<List<UserInternshipEntity>> getLecturerByInternship(@PathVariable String internshipId){
        return ResponseEntity.ok(userInternshipService.getLecturerByInternship(internshipId));
    }
}
