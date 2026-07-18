package com.Hiep.B23DCCN295.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hiep.B23DCCN295.dto.request.InternshipRequest;
import com.Hiep.B23DCCN295.entity.InternshipEntity;
import com.Hiep.B23DCCN295.service.InternshipService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internship")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @PostMapping("/createInternship")
    public ResponseEntity<InternshipEntity> createInternship(@Valid @RequestBody InternshipRequest request) {
        return ResponseEntity.ok(internshipService.createInternship(request));
    }
    @PatchMapping("/updateInternship/{id}")
    public ResponseEntity<InternshipEntity> updateInternship(@Valid @RequestBody InternshipRequest request,@PathVariable String id){
        return ResponseEntity.ok(internshipService.updateInternship(request, id));
    }
    @PostMapping("/deleteInternship/{id}")
    public void deleteInternship(@PathVariable String id){
        internshipService.deleteInternship(id);
    }
    @GetMapping("/getInternshipById/{id}")
    public ResponseEntity<InternshipEntity> getInternshipById(@PathVariable String id){
        return ResponseEntity.ok(internshipService.getInternshipById(id));
    }
    @GetMapping("/getAllInternship")
    public ResponseEntity<List<InternshipEntity>> getAllInternship(){
        return ResponseEntity.ok(internshipService.getAllInternship());
    }
}