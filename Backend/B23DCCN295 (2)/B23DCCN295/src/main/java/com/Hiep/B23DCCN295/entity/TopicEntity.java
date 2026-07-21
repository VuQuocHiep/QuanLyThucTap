package com.Hiep.B23DCCN295.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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

import com.Hiep.B23DCCN295.enums.DifficultyTopic;
import com.Hiep.B23DCCN295.enums.StatusStudentTopic;
import com.Hiep.B23DCCN295.enums.StatusTopic;
import com.Hiep.B23DCCN295.enums.TechnologyTopic;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "topic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String topicId;

    String title;

    String description;

    int maxStudent;

    @ManyToOne
    @JoinColumn(name = "lecturerId")
    UserEntity lecturer;

    @Enumerated(EnumType.STRING)
    StatusTopic status = StatusTopic.OPENING;

    @Enumerated(EnumType.STRING)
    DifficultyTopic difficulty=DifficultyTopic.HARD;

    boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "topicGroupId")
    TopicGroupEntity topicGroup;

    @ManyToOne
    @JoinColumn(name = "internshipId")
    InternshipEntity internship;

    @Enumerated(EnumType.STRING)
    StatusStudentTopic statusStudent;

    @ManyToOne
    @JoinColumn(name = "studentId")
    UserEntity student;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    List<TechnologyTopic> technology;

    int semester;

    int year;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}