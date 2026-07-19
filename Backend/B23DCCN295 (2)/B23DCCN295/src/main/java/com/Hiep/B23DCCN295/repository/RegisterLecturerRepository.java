package com.Hiep.B23DCCN295.repository;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hiep.B23DCCN295.entity.RegisterLecturerEntity;
import com.Hiep.B23DCCN295.enums.StatusRegisterLecturer;

public interface RegisterLecturerRepository extends JpaRepository<RegisterLecturerEntity,String>{
    boolean existsByStudent_UserIdAndInternship_InternshipIdAndStatus(
        String userId,
        String internshipId,
        StatusRegisterLecturer status
    );
    int countByStudent_UserIdAndInternship_InternshipIdAndStatus(
        String studentId,
        String internshipId,
        StatusRegisterLecturer status
    );
    List<RegisterLecturerEntity> findByStudent_UserIdAndInternship_InternshipId(
        String studentId,
        String internshipId
    );
    List<RegisterLecturerEntity> findByLecturer_UserIdAndInternship_InternshipIdAndStatus(
        String lecturerId,
        String internshipId,
        StatusRegisterLecturer status
    );
}
