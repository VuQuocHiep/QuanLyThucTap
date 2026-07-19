package com.Hiep.B23DCCN295.dto.request;

import com.Hiep.B23DCCN295.enums.ResultUserInternship;
import com.Hiep.B23DCCN295.enums.TypeUserInternship;

import jakarta.validation.constraints.Min;

import com.Hiep.B23DCCN295.enums.GradeUserInternship;

import lombok.Data;

@Data
public class UserInternshipRequest {

    private String userId;

    private String internshipId;

    private TypeUserInternship type;
    @Min(1)
    private Integer maxStudent;
    @Min(0)
    private double finalScore;

    private GradeUserInternship grade;

    private ResultUserInternship result;
}