package com.Hiep.B23DCCN295.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.TopicRequest;
import com.Hiep.B23DCCN295.entity.InternshipEntity;
import com.Hiep.B23DCCN295.entity.TopicEntity;
import com.Hiep.B23DCCN295.entity.TopicGroupEntity;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.enums.StatusTopic;
import com.Hiep.B23DCCN295.mapper.TopicMapper;
import com.Hiep.B23DCCN295.repository.InternshipRepository;
import com.Hiep.B23DCCN295.repository.TopicGroupRepository;
import com.Hiep.B23DCCN295.repository.TopicRepository;
import com.Hiep.B23DCCN295.repository.UserRopository;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserRopository userRopository;

    @Autowired
    private TopicGroupRepository topicGroupRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    public TopicEntity createTopic(TopicRequest request){
        TopicEntity topicEntity = topicMapper.toEntity(request);

        UserEntity lecturerEntity = userRopository
            .findById(request.getLecturerId())
            .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        InternshipEntity internshipEntity = internshipRepository
            .findById(request.getInternshipId())
            .orElseThrow(() -> new RuntimeException("Internship not found"));

        TopicGroupEntity topicGroupEntity = topicGroupRepository
            .findById(request.getTopicGroupId())
            .orElseThrow(() -> new RuntimeException("Topic group not found"));

        topicEntity.setLecturer(lecturerEntity);
        topicEntity.setInternship(internshipEntity);
        topicEntity.setTopicGroup(topicGroupEntity);

        if(request.getStudentId() != null){
            UserEntity studentEntity = userRopository
                .findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

            topicEntity.setStudent(studentEntity);
        }

        return topicRepository.save(topicEntity);
    }

    public TopicEntity updateTopic(String id, TopicRequest request){
        if(!topicRepository.existsById(id)){
            throw new RuntimeException("Topic not found");
        }

        TopicEntity topicEntity = topicMapper.toEntity(request);

        UserEntity lecturerEntity = userRopository
            .findById(request.getLecturerId())
            .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        InternshipEntity internshipEntity = internshipRepository
            .findById(request.getInternshipId())
            .orElseThrow(() -> new RuntimeException("Internship not found"));

        TopicGroupEntity topicGroupEntity = topicGroupRepository
            .findById(request.getTopicGroupId())
            .orElseThrow(() -> new RuntimeException("Topic group not found"));

        topicEntity.setTopicId(id);
        topicEntity.setLecturer(lecturerEntity);
        topicEntity.setInternship(internshipEntity);
        topicEntity.setTopicGroup(topicGroupEntity);

        if(request.getStudentId() != null){
            UserEntity studentEntity = userRopository
                .findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

            topicEntity.setStudent(studentEntity);
        }

        return topicRepository.save(topicEntity);
    }

    public TopicEntity deleteTopic(String id){
        if(!topicRepository.existsById(id)){
            throw new RuntimeException("Topic not found");
        }

        TopicEntity topicEntity = topicRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Topic not found"));

        topicEntity.setDeleted(true);

        return topicRepository.save(topicEntity);
    }

    public TopicEntity getTopicById(String id){
        return topicRepository
            .findByTopicIdAndDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    public List<TopicEntity> getAllTopic(){
        return topicRepository.findByDeletedFalse();
    }

    public List<TopicEntity> getAllTopicByLecturer(String lecturerId){
        return topicRepository
            .findByLecturer_UserIdAndDeletedFalse(lecturerId);
    }

    public List<TopicEntity> getAllTopicByLecturerAndInternship(
        String lecturerId,
        String internshipId
    ){
        return topicRepository
            .findByLecturer_UserIdAndInternship_InternshipIdAndDeletedFalse(
                lecturerId,
                internshipId
            );
    }

    public TopicEntity changeStatus(String id){
        TopicEntity topicEntity = topicRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Topic not found"));

        if(topicEntity.getStatus() == StatusTopic.OPENING){
            topicEntity.setStatus(StatusTopic.CLOSED);
        }else{
            topicEntity.setStatus(StatusTopic.OPENING);
        }

        return topicRepository.save(topicEntity);
    }
}