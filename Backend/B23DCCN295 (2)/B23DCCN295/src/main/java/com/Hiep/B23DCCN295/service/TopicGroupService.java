package com.Hiep.B23DCCN295.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.TopicGroupRequest;
import com.Hiep.B23DCCN295.entity.TopicGroupEntity;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.mapper.TopicGroupMapper;
import com.Hiep.B23DCCN295.repository.TopicGroupRepository;
import com.Hiep.B23DCCN295.repository.UserRopository;

@Service
public class TopicGroupService {
    @Autowired
    private TopicGroupRepository topicGroupRepository;
    @Autowired
    private TopicGroupMapper topicGroupMapper;
    @Autowired
    private UserRopository userRopository;
    public TopicGroupEntity createTopicGroup(TopicGroupRequest request){
        if(topicGroupRepository.existsByName(request.getName())){
            throw new RuntimeException("Đã tồn tại");
        }
        TopicGroupEntity topicGroupEntity = topicGroupMapper.toEntity(request);
        UserEntity userEntity = userRopository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("Not found"));
        topicGroupEntity.setUser(userEntity);
        return topicGroupRepository.save(topicGroupEntity);
    }
    public TopicGroupEntity updateTopicGroup(String id,TopicGroupRequest request){
        if(!topicGroupRepository.existsById(id)){
            throw new RuntimeException("Not found");
        }
        TopicGroupEntity topicGroupEntity = topicGroupMapper.toEntity(request);
        UserEntity userEntity = userRopository.findById(request.getUserId()).orElseThrow(()->new RuntimeException("Not found"));
        topicGroupEntity.setUser(userEntity);
        return topicGroupRepository.save(topicGroupEntity);
    }
    public TopicGroupEntity deleteTopicGroup(String id){
        if(!topicGroupRepository.existsById(id)){
            throw new RuntimeException("Not found");
        }
        TopicGroupEntity topicGroupEntity = topicGroupRepository.findById(id).orElseThrow(()->new RuntimeException("Not found"));
        topicGroupEntity.setDeleted(true);
        return topicGroupRepository.save(topicGroupEntity);
    }
    public List<TopicGroupEntity> getAllTopicGroup(){
        return topicGroupRepository.findByDeletedFalse();
    }
}
