package com.Hiep.B23DCCN295.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hiep.B23DCCN295.entity.TopicEntity;

public interface TopicRepository
    extends JpaRepository<TopicEntity, String> {

    Optional<TopicEntity> findByTopicIdAndDeletedFalse(String topicId);

    List<TopicEntity> findByDeletedFalse();

    List<TopicEntity> findByLecturer_UserIdAndDeletedFalse(
        String lecturerId
    );

    List<TopicEntity>
    findByLecturer_UserIdAndInternship_InternshipIdAndDeletedFalse(
        String lecturerId,
        String internshipId
    );
}