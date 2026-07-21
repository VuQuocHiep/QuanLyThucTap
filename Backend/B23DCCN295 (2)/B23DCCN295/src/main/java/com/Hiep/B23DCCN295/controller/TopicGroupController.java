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

import com.Hiep.B23DCCN295.dto.request.TopicGroupRequest;
import com.Hiep.B23DCCN295.entity.TopicGroupEntity;
import com.Hiep.B23DCCN295.repository.TopicGroupRepository;
import com.Hiep.B23DCCN295.service.TopicGroupService;

@RestController
@RequestMapping("/topicGroup")
public class TopicGroupController {
    @Autowired
    private TopicGroupService topicGroupService;
    @PostMapping("/createTopicGroup")
    public TopicGroupEntity createTopicGroup(@Valid @RequestBody TopicGroupRequest request){
        return topicGroupService.createTopicGroup(request);
    }
    @PatchMapping("/updateTopicGroup/{id}")
    public TopicGroupEntity updateTopicGroup(@Valid @RequestBody TopicGroupRequest request,@PathVariable String id){
        return topicGroupService.updateTopicGroup(id, request);
    }
    @PostMapping("/deleteTopicGroup/{id}")
    public TopicGroupEntity deleteTopicGroup(@PathVariable String id){
        return topicGroupService.deleteTopicGroup(id);
    }
    @GetMapping("/getAllTopicGroup")
    public List<TopicGroupEntity> getAllTopicGroup(){
        return topicGroupService.getAllTopicGroup();
    }
}
