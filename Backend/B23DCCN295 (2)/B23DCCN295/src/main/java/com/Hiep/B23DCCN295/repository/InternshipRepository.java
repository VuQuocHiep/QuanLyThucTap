package com.Hiep.B23DCCN295.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Hiep.B23DCCN295.entity.InternshipEntity;

public interface InternshipRepository extends JpaRepository<InternshipEntity,String>{
    Optional <InternshipEntity> findByName(String name);
    Optional <InternshipEntity> findById(String id);
    boolean existsByName(String name);
    boolean existsById(String id);
    @Query("Select i from InternshipEntity  i where i.id=:id and i.deleted=false")
    InternshipEntity getInternshipById(@Param("id") String id);
    @Query("Select i from InternshipEntity  i where i.deleted=false")
    List<InternshipEntity> getAllInternship();
}
