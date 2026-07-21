package com.Hiep.B23DCCN295.mapper;

import org.mapstruct.Mapper;

import com.Hiep.B23DCCN295.dto.request.TopicRequest;
import com.Hiep.B23DCCN295.entity.TopicEntity;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicEntity toEntity(TopicRequest request);
}
