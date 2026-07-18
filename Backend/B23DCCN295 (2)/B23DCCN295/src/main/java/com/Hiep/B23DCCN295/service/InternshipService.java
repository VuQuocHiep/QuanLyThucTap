package com.Hiep.B23DCCN295.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.InternshipRequest;
import com.Hiep.B23DCCN295.entity.InternshipEntity;
import com.Hiep.B23DCCN295.mapper.InternshipMapper;
import com.Hiep.B23DCCN295.repository.InternshipRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
public class InternshipService {
    private final InternshipRepository internshipRepository;
    private final InternshipMapper internshipMapper;
    public InternshipEntity createInternship(InternshipRequest request){
        if(internshipRepository.existsByName(request.getName())){
            throw new RuntimeException("Đã tồn tại");
        }
        InternshipEntity internshipEntity = internshipMapper.toEntity(request);
        return internshipRepository.save(internshipEntity);
    }
    public InternshipEntity updateInternship(InternshipRequest request,String id){
        InternshipEntity internshipEntity = internshipMapper.toEntity(request);
        if(!internshipRepository.existsById(id)){
            throw new RuntimeException("Not found!");
        }
        return internshipRepository.save(internshipEntity);
    }
    public void deleteInternship(String id){
        if(!internshipRepository.existsById(id)){
            throw new RuntimeException("Not found!");
        }
        InternshipEntity internshipEntity = internshipRepository.findById(id).orElseThrow(()->new RuntimeException("Not found!"));
        internshipEntity.setDeleted(true);
        internshipRepository.save(internshipEntity);
    }
    public InternshipEntity getInternshipById(String id){
        if(!internshipRepository.existsById(id)){
            throw new RuntimeException("Not found!");
        }
        InternshipEntity internshipEntity = internshipRepository.getInternshipById(id);
        return internshipEntity;
    }
    public List<InternshipEntity> getAllInternship(){
        return internshipRepository.getAllInternship();
    }
}
