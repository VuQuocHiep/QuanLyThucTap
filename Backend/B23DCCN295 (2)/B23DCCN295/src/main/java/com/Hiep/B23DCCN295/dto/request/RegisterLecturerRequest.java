package com.Hiep.B23DCCN295.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterLecturerRequest {
    String studentId;
    String lecturerId;
    String internshipId;
    String status;
}
