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

import com.Hiep.B23DCCN295.enums.StatusRegisterLecturer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "registerLecturer")
public class RegisterLecturerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String registerLecturerId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "studentId")
    UserEntity student;
    @ManyToOne(optional = false)
    @JoinColumn(name = "lecturerId")
    UserEntity lecturer;
    @ManyToOne(optional = false)
    @JoinColumn(name = "internshipId")
    InternshipEntity internship;
    @Enumerated(EnumType.STRING)
    StatusRegisterLecturer status = StatusRegisterLecturer.PENDING;
    @CreationTimestamp 
    @Column(updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
