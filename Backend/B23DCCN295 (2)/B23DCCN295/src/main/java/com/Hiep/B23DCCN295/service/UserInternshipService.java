package com.Hiep.B23DCCN295.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.UserInternshipRequest;
import com.Hiep.B23DCCN295.entity.InternshipEntity;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.entity.UserInternshipEntity;
import com.Hiep.B23DCCN295.repository.InternshipRepository;
import com.Hiep.B23DCCN295.repository.UserInternshipRepository;
import com.Hiep.B23DCCN295.repository.UserRopository;

@Service
public class UserInternshipService {
    @Autowired
    private UserInternshipRepository userInternshipRepository;
    @Autowired
    private UserRopository userRopository;
    @Autowired
    private InternshipRepository internshipRepository;
    public UserInternshipEntity createUserInternship(UserInternshipRequest request) {

        UserEntity user = userRopository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        InternshipEntity internship = internshipRepository
                .findById(request.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship không tồn tại"));

        if (userInternshipRepository
                .existsByUserEntity_UserIdAndInternshipEntity_InternshipId(
                        request.getUserId(),
                        request.getInternshipId())) {

            throw new RuntimeException("User đã đăng ký kỳ thực tập này");
        }

        UserInternshipEntity entity = new UserInternshipEntity();

        entity.setUserEntity(user);
        entity.setInternshipEntity(internship);
        entity.setType(request.getType());
        entity.setMaxStudent(request.getMaxStudent());
        entity.setFinalScore(request.getFinalScore());
        entity.setGrade(request.getGrade());
        entity.setResult(request.getResult());

        return userInternshipRepository.save(entity);
    }
    public List<UserInternshipEntity> getAllUserInternship(){
        return userInternshipRepository.findAll();
    }
    public List<UserInternshipEntity> getByUser(String userId){
        List<UserInternshipEntity> u = userInternshipRepository.findByUserEntity_UserId(userId);
        if(u.isEmpty()){
            throw new RuntimeException("Not found!");
        }
        return u;
    }
    public List<UserInternshipEntity> getByInternship(String internshipId){
        List<UserInternshipEntity> u = userInternshipRepository.findByInternshipEntity_InternshipId(internshipId);
        if(u.isEmpty()){
            throw new RuntimeException("Not found!");
        }
        return u;
    }
    public List<UserInternshipEntity> getStudentByInternship(String internshipId){
        List<UserInternshipEntity> u = userInternshipRepository.findByInternshipEntity_InternshipIdAndType(internshipId,"STUDENT");
        if(u.isEmpty()){
            throw new RuntimeException("Not found!");
        }
        return u;
    }
    public List<UserInternshipEntity> getLecturerByInternship(String internshipId){
        List<UserInternshipEntity> u = userInternshipRepository.findByInternshipEntity_InternshipIdAndType(internshipId,"LECTURER");
        if(u.isEmpty()){
            throw new RuntimeException("Not found!");
        }
        return u;
    }
    
}
