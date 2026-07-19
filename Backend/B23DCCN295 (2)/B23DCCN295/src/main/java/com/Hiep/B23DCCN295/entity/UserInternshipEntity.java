package com.Hiep.B23DCCN295.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.Hiep.B23DCCN295.enums.GradeUserInternship;
import com.Hiep.B23DCCN295.enums.ResultUserInternship;
import com.Hiep.B23DCCN295.enums.TypeUserInternship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "userInternship")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInternshipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userInternshipId;
    @Enumerated(EnumType.STRING)
    TypeUserInternship type = TypeUserInternship.STUDENT;
    int maxStudent;
    double finalScore;
    @Enumerated(EnumType.STRING)
    GradeUserInternship grade = GradeUserInternship.B_Plus;
    @Enumerated(EnumType.STRING)
    ResultUserInternship result = ResultUserInternship.PASS;
    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    UserEntity userEntity;
    @ManyToOne(optional = false)
    @JoinColumn(name = "internshipId")
    InternshipEntity internshipEntity;
}
