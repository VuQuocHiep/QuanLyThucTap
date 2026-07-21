package com.Hiep.B23DCCN295.mapper;

import org.mapstruct.Mapper;

import com.Hiep.B23DCCN295.dto.request.TopicGroupRequest;
import com.Hiep.B23DCCN295.entity.TopicGroupEntity;

@Mapper(componentModel = "spring")
public interface TopicGroupMapper {
    TopicGroupEntity toEntity(TopicGroupRequest request);
}
