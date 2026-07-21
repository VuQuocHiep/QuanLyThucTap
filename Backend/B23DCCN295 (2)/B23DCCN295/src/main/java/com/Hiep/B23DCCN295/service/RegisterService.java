package com.Hiep.B23DCCN295.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.RegisterRequest;
import com.Hiep.B23DCCN295.entity.InternshipEntity;
import com.Hiep.B23DCCN295.entity.RegisterEntity;
import com.Hiep.B23DCCN295.entity.RegisterLecturerEntity;
import com.Hiep.B23DCCN295.entity.TopicEntity;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.enums.StatusRegister;
import com.Hiep.B23DCCN295.enums.StatusStudentTopic;
import com.Hiep.B23DCCN295.enums.StatusTopic;
import com.Hiep.B23DCCN295.repository.InternshipRepository;
import com.Hiep.B23DCCN295.repository.RegisterLecturerRepository;
import com.Hiep.B23DCCN295.repository.RegisterRepository;
import com.Hiep.B23DCCN295.repository.TopicRepository;
import com.Hiep.B23DCCN295.repository.UserRopository;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private UserRopository userRopository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private RegisterLecturerRepository registerLecturerRepository;

    public RegisterEntity createRegister(RegisterRequest request){
        UserEntity studentEntity = getCurrentUser();

        boolean exist = registerRepository
            .existsByStudent_UserIdAndInternship_InternshipIdAndStatusNot(
                studentEntity.getUserId(),
                request.getInternshipId(),
                StatusRegister.REJECTED
            );

        if(exist){
            throw new RuntimeException(
                "Bạn đã đăng ký hoặc yêu cầu đang chờ xử lý"
            );
        }

        TopicEntity topicEntity = topicRepository
            .findById(request.getTopicId())
            .orElseThrow(() ->
                new RuntimeException("Topic not found")
            );

        InternshipEntity internshipEntity = internshipRepository
            .findById(request.getInternshipId())
            .orElseThrow(() ->
                new RuntimeException("Internship not found")
            );

        RegisterEntity registerEntity = new RegisterEntity();

        registerEntity.setTopic(topicEntity);
        registerEntity.setStudent(studentEntity);
        registerEntity.setInternship(internshipEntity);
        registerEntity.setReasonByStudent(
            request.getReasonByStudent()
        );
        registerEntity.setStatus(StatusRegister.PENDING);

        return registerRepository.save(registerEntity);
    }

    public List<RegisterEntity> getAllRegister(){
        return registerRepository.findAll();
    }

    public List<RegisterEntity> getOneByStudent(
        String internshipId
    ){
        UserEntity studentEntity = getCurrentUser();

        return registerRepository
            .findByStudent_UserIdAndInternship_InternshipId(
                studentEntity.getUserId(),
                internshipId
            );
    }

    public RegisterEntity updateRegister(
        String id,
        RegisterRequest request
    ){
        RegisterEntity registerEntity = registerRepository
            .findById(id)
            .orElseThrow(() ->
                new RuntimeException("Register not found")
            );

        if(request.getStatus() != null){
            registerEntity.setStatus(StatusRegister.valueOf(request.getStatus()));
        }

        registerEntity.setReasonByLecturer(
            request.getReasonByLecturer()
        );

        return registerRepository.save(registerEntity);
    }

    public void deleteRegister(String id){
        RegisterEntity registerEntity = registerRepository
            .findById(id)
            .orElseThrow(() ->
                new RuntimeException("Register not found")
            );

        registerRepository.delete(registerEntity);
    }

    public List<RegisterEntity> getByStudent(String userId){
        return registerRepository.findByStudent_UserId(
            userId
        );
    }

    public List<RegisterEntity> getByTopic(String topicId){
        return registerRepository.findByTopic_TopicId(topicId);
    }

    public List<RegisterEntity> getRegisterByLecturer(
        String internshipId
    ){
        UserEntity lecturerEntity = getCurrentUser();

        List<RegisterLecturerEntity> registerLecturers =
            registerLecturerRepository
                .findByLecturer_UserIdAndInternship_InternshipId(
                    lecturerEntity.getUserId(),
                    internshipId
                );

        List<String> studentIds = new ArrayList<>();

        for(RegisterLecturerEntity item : registerLecturers){
            studentIds.add(
                item.getStudent().getUserId()
            );
        }

        if(studentIds.isEmpty()){
            return new ArrayList<>();
        }

        return registerRepository
            .findByStudent_UserIdInAndInternship_InternshipId(
                studentIds,
                internshipId
            );
    }

    public RegisterEntity updateStatusByLecturer(
        String id,
        RegisterRequest request
    ){
        RegisterEntity registerEntity = registerRepository
            .findById(id)
            .orElseThrow(() ->
                new RuntimeException("Register not found")
            );

        if(
            StatusRegister.valueOf(request.getStatus()) != StatusRegister.APPROVED &&
            StatusRegister.valueOf(request.getStatus()) != StatusRegister.REJECTED
        ){
            throw new RuntimeException(
                "Status chỉ được APPROVED hoặc REJECTED"
            );
        }

        registerEntity.setStatus(StatusRegister.valueOf(request.getStatus()));
        registerEntity.setReasonByLecturer(
            request.getReasonByLecturer()
        );

        return registerRepository.save(registerEntity);
    }

    public RegisterEntity approveTopic(String topicId){
        TopicEntity topicEntity = topicRepository
            .findById(topicId)
            .orElseThrow(() ->
                new RuntimeException("Topic not found")
            );

        if(topicEntity.getStudent() == null){
            throw new RuntimeException(
                "Topic chưa có sinh viên"
            );
        }

        topicEntity.setStatusStudent(StatusStudentTopic.APPROVAL);
        topicEntity.setStatus(StatusTopic.OPENING);

        topicRepository.save(topicEntity);

        RegisterEntity registerEntity = new RegisterEntity();

        registerEntity.setTopic(topicEntity);
        registerEntity.setStudent(topicEntity.getStudent());
        registerEntity.setInternship(
            topicEntity.getInternship()
        );
        registerEntity.setStatus(StatusRegister.APPROVED);
        registerEntity.setReasonByLecturer(
            "Giảng viên đã đồng ý với đề tài sinh viên mong muốn"
        );
        registerEntity.setReasonByStudent(
            "Đề tài sinh viên mong muốn"
        );

        return registerRepository.save(registerEntity);
    }

    public RegisterEntity rejectTopic(String topicId){
        TopicEntity topicEntity = topicRepository
            .findById(topicId)
            .orElseThrow(() ->
                new RuntimeException("Topic not found")
            );

        if(topicEntity.getStudent() == null){
            throw new RuntimeException(
                "Topic chưa có sinh viên"
            );
        }

        topicEntity.setStatusStudent(StatusStudentTopic.REJECT);

        topicRepository.save(topicEntity);

        RegisterEntity registerEntity = new RegisterEntity();

        registerEntity.setTopic(topicEntity);
        registerEntity.setStudent(topicEntity.getStudent());
        registerEntity.setInternship(
            topicEntity.getInternship()
        );
        registerEntity.setStatus(StatusRegister.REJECTED);
        registerEntity.setReasonByLecturer(
            "Giảng viên không đồng ý với đề tài của em"
        );
        registerEntity.setReasonByStudent(
            "Đề tài sinh viên mong muốn"
        );

        return registerRepository.save(registerEntity);
    }

    private UserEntity getCurrentUser(){
        String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

        return userRopository
            .findByEmail(email);
    }
}