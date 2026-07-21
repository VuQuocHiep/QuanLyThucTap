package com.Hiep.B23DCCN295.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hiep.B23DCCN295.entity.TopicGroupEntity;

public interface TopicGroupRepository extends JpaRepository<TopicGroupEntity,String>{
    boolean existsByName(String name);
    boolean existsById(String id);
    Optional<TopicGroupEntity> findById(String id);
    List<TopicGroupEntity> findByDeletedFalse();

}
