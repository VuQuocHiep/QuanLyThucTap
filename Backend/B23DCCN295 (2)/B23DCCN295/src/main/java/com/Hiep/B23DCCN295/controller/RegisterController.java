package com.Hiep.B23DCCN295.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hiep.B23DCCN295.dto.request.RegisterRequest;
import com.Hiep.B23DCCN295.entity.RegisterEntity;
import com.Hiep.B23DCCN295.service.RegisterService;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/createRegister")
    public RegisterEntity createRegister(
        @Valid @RequestBody RegisterRequest request
    ){
        return registerService.createRegister(request);
    }

    @GetMapping("/getAllRegister")
    public List<RegisterEntity> getAllRegister(){
        return registerService.getAllRegister();
    }

    @GetMapping("/getOneByStudent/{internshipId}")
    public List<RegisterEntity> getOneByStudent(
        @PathVariable String internshipId
    ){
        return registerService.getOneByStudent(
            internshipId
        );
    }

    @PatchMapping("/updateRegister/{id}")
    public RegisterEntity updateRegister(
        @PathVariable String id,
        @RequestBody RegisterRequest request
    ){
        return registerService.updateRegister(
            id,
            request
        );
    }

    @DeleteMapping("/deleteRegister/{id}")
    public String deleteRegister(
        @PathVariable String id
    ){
        registerService.deleteRegister(id);

        return "Delete register success";
    }

    @GetMapping("/getByStudent/{userId}")
    public List<RegisterEntity> getByStudent(@PathVariable String userId){
        return registerService.getByStudent(userId);
    }

    @GetMapping("/getByTopic/{topicId}")
    public List<RegisterEntity> getByTopic(
        @PathVariable String topicId
    ){
        return registerService.getByTopic(topicId);
    }

    @GetMapping(
        "/getRegisterByLecturer/{internshipId}"
    )
    public List<RegisterEntity> getRegisterByLecturer(
        @PathVariable String internshipId
    ){
        return registerService
            .getRegisterByLecturer(internshipId);
    }

    @PatchMapping(
        "/updateStatusByLecturer/{id}"
    )
    public RegisterEntity updateStatusByLecturer(
        @PathVariable String id,
        @RequestBody RegisterRequest request
    ){
        return registerService
            .updateStatusByLecturer(id, request);
    }

    @PostMapping("/approveTopic/{topicId}")
    public RegisterEntity approveTopic(
        @PathVariable String topicId
    ){
        return registerService.approveTopic(topicId);
    }

    @PostMapping("/rejectTopic/{topicId}")
    public RegisterEntity rejectTopic(
        @PathVariable String topicId
    ){
        return registerService.rejectTopic(topicId);
    }
}