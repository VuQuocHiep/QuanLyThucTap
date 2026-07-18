package com.Hiep.B23DCCN295.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.Hiep.B23DCCN295.enums.StatusInternship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "internship")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     String id;
     String name;
     String description;
     int semester;
     int year;
     LocalDate registerOpenDate;
     LocalDate registerCloseDate;

     LocalDate startDate = LocalDate.now();

     LocalDate endDate = LocalDate.now().plusDays(90);

    @Enumerated(EnumType.STRING)
     StatusInternship status = StatusInternship.OPENING;

     int amountStudent = 20;

     boolean deleted = false;

    @CreationTimestamp
    @Column(updatable = false)
     LocalDateTime createdAt;

    @UpdateTimestamp
     LocalDateTime updatedAt;
}
