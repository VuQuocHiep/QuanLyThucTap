package com.Hiep.B23DCCN295.dto.request;

import java.time.LocalDate;

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
public class InternshipRequest {
    @Size(min = 1)
    String name;
    String description;
    @Min(1)
    @Max(3)
    int semester;
    int year;
    LocalDate registerOpenDate;
    LocalDate registerCloseDate;
    String status;
}
