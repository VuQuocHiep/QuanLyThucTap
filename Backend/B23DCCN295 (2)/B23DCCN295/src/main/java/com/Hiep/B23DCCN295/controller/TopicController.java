package com.Hiep.B23DCCN295.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hiep.B23DCCN295.dto.request.TopicRequest;
import com.Hiep.B23DCCN295.entity.TopicEntity;
import com.Hiep.B23DCCN295.service.TopicService;

@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/createTopic")
    public TopicEntity createTopic(
        @Valid @RequestBody TopicRequest request
    ){
        return topicService.createTopic(request);
    }

    @PatchMapping("/updateTopic/{id}")
    public TopicEntity updateTopic(
        @Valid @RequestBody TopicRequest request,
        @PathVariable String id
    ){
        return topicService.updateTopic(id, request);
    }

    @PostMapping("/deleteTopic/{id}")
    public TopicEntity deleteTopic(
        @PathVariable String id
    ){
        return topicService.deleteTopic(id);
    }

    @GetMapping("/getTopicById/{id}")
    public TopicEntity getTopicById(
        @PathVariable String id
    ){
        return topicService.getTopicById(id);
    }

    @GetMapping("/getAllTopic")
    public List<TopicEntity> getAllTopic(){
        return topicService.getAllTopic();
    }

    @GetMapping("/getAllTopicByLecturer/{lecturerId}")
    public List<TopicEntity> getAllTopicByLecturer(
        @PathVariable String lecturerId
    ){
        return topicService.getAllTopicByLecturer(lecturerId);
    }

    @GetMapping(
        "/getAllTopicByLecturerAndInternship/{lecturerId}/{internshipId}"
    )
    public List<TopicEntity> getAllTopicByLecturerAndInternship(
        @PathVariable String lecturerId,
        @PathVariable String internshipId
    ){
        return topicService.getAllTopicByLecturerAndInternship(
            lecturerId,
            internshipId
        );
    }

    @PatchMapping("/changeStatus/{id}")
    public TopicEntity changeStatus(
        @PathVariable String id
    ){
        return topicService.changeStatus(id);
    }
}