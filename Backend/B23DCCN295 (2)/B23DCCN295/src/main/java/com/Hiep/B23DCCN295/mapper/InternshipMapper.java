package com.Hiep.B23DCCN295.mapper;

import org.mapstruct.Mapper;

import com.Hiep.B23DCCN295.dto.request.InternshipRequest;
import com.Hiep.B23DCCN295.entity.InternshipEntity;

@Mapper(componentModel = "spring")
public interface InternshipMapper {
    InternshipEntity toEntity(InternshipRequest request);
}
