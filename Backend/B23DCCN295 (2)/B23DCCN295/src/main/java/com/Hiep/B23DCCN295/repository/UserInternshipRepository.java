package com.Hiep.B23DCCN295.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hiep.B23DCCN295.entity.UserInternshipEntity;
import com.Hiep.B23DCCN295.enums.TypeUserInternship;

public interface UserInternshipRepository
        extends JpaRepository<UserInternshipEntity, String> {

    
    Optional<UserInternshipEntity>
        findByUserEntity_UserIdAndInternshipEntity_InternshipId(
            String userId,
            String internshipId
        );

    List<UserInternshipEntity>
        findByUserEntity_UserId(String userId);

    List<UserInternshipEntity>
        findByInternshipEntity_InternshipId(String internshipId);

    List<UserInternshipEntity>
        findByInternshipEntity_InternshipIdAndUserEntity_UserIdAndType(
            String internshipId,
            String userId,
            TypeUserInternship type
        );

    boolean existsByUserEntity_UserIdAndInternshipEntity_InternshipId(
        String userId,
        String internshipId
    );
}