package com.Hiep.B23DCCN295.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.Hiep.B23DCCN295.enums.StatusRegister;

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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "register")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String registerId;

    @ManyToOne
    @JoinColumn(name = "topicId")
    TopicEntity topic;

    @ManyToOne
    @JoinColumn(name = "studentId")
    UserEntity student;

    @ManyToOne
    @JoinColumn(name = "internshipId")
    InternshipEntity internship;

    String reasonByStudent;

    String reasonByLecturer;

    @Enumerated(EnumType.STRING)
    StatusRegister status = StatusRegister.PENDING;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}