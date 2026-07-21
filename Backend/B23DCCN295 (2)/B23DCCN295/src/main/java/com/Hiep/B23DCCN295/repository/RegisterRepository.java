package com.Hiep.B23DCCN295.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hiep.B23DCCN295.entity.RegisterEntity;
import com.Hiep.B23DCCN295.enums.StatusRegister;

public interface RegisterRepository
    extends JpaRepository<RegisterEntity, String> {

    boolean existsByStudent_UserIdAndInternship_InternshipIdAndStatusNot(
        String studentId,
        String internshipId,
        StatusRegister status
    );

    List<RegisterEntity>
    findByStudent_UserIdAndInternship_InternshipId(
        String studentId,
        String internshipId
    );

    List<RegisterEntity> findByStudent_UserId(
        String studentId
    );

    List<RegisterEntity> findByTopic_TopicId(
        String topicId
    );

    List<RegisterEntity>
    findByStudent_UserIdInAndInternship_InternshipId(
        List<String> studentIds,
        String internshipId
    );
}