package com.Hiep.B23DCCN295.dto.request;

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
public class RegisterRequest {

    String topicId;

    String studentId;

    String internshipId;

    @Size(max = 500)
    String reasonByStudent;

    @Size(max = 500)
    String reasonByLecturer;

    String status;
}