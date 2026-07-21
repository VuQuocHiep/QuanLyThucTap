package com.Hiep.B23DCCN295.dto.request;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicRequest {
    @Size(min = 1)
    String title;
    String description;
    @Min(1)
    int maxStudent;
    String lecturerId;
    String status;
    String difficulty;
    String topicGroupId;
    String internshipId;
    String statusStudent;
    String studentId;
    List<String> technology;
    @Min(1)
    @Max(3)
    int semester;
    int year;
}