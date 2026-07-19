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

import com.Hiep.B23DCCN295.dto.request.RegisterLecturerRequest;
import com.Hiep.B23DCCN295.entity.RegisterLecturerEntity;
import com.Hiep.B23DCCN295.service.RegisterLecturerService;

@RestController
@RequestMapping("/registerLecturer")
public class RegisterLecturerController {
    @Autowired
    private RegisterLecturerService registerLecturerService;
    @PostMapping("/createRegisterLecturer")
    public ResponseEntity<RegisterLecturerEntity> createRegisterLecturer(@Valid @RequestBody RegisterLecturerRequest request){
        return ResponseEntity.ok(registerLecturerService.createRegisterLecturer(request));
    }
    @GetMapping("/student/{userId}/internship/{internshipId}")
    public List<RegisterLecturerEntity> getLecturerByStudentInternship(
            @PathVariable String userId,
            @PathVariable String internshipId) {

        return registerLecturerService.getLecturerByStudentInternship(userId, internshipId);
    }

    @GetMapping("/lecturer/{userId}/internship/{internshipId}")
    public List<RegisterLecturerEntity> getStudentsByLecturersInternship(
            @PathVariable String userId,
            @PathVariable String internshipId) {

        return registerLecturerService.getStudentsByLecturersInternship(userId, internshipId);
    }
}
