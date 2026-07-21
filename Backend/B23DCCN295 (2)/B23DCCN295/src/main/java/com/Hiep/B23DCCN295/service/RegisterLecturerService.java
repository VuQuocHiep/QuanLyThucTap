package com.Hiep.B23DCCN295.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hiep.B23DCCN295.dto.request.RegisterLecturerRequest;
import com.Hiep.B23DCCN295.entity.InternshipEntity;
import com.Hiep.B23DCCN295.entity.RegisterLecturerEntity;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.entity.UserInternshipEntity;
import com.Hiep.B23DCCN295.enums.StatusRegisterLecturer;
import com.Hiep.B23DCCN295.repository.InternshipRepository;
import com.Hiep.B23DCCN295.repository.RegisterLecturerRepository;
import com.Hiep.B23DCCN295.repository.UserInternshipRepository;
import com.Hiep.B23DCCN295.repository.UserRopository;

@Service
public class RegisterLecturerService {
    @Autowired
    private RegisterLecturerRepository registerLecturerRepository;
    @Autowired
    private InternshipRepository internshipRepository;
    @Autowired
    private UserInternshipRepository userInternshipRepository;
    @Autowired 
    private UserRopository userRopository;
    public RegisterLecturerEntity createRegisterLecturer(RegisterLecturerRequest request){
        LocalDate now = LocalDate.now();
        InternshipEntity internshipEntity = internshipRepository.getInternshipById(request.getInternshipId());
        if (now.isBefore(internshipEntity.getRegisterOpenDate())
                || now.isAfter(internshipEntity.getRegisterCloseDate())) {
            throw new RuntimeException("Ngoài thời gian đăng ký");
        }
        boolean exists = userInternshipRepository.existsByUserEntity_UserIdAndInternshipEntity_InternshipId(request.getLecturerId(),request.getInternshipId());
        if(!exists){
            throw new RuntimeException("Giảng viên không thuộc đợt thực tập");
        }
        boolean exists1 = registerLecturerRepository.existsByStudent_UserIdAndInternship_InternshipIdAndStatus(request.getStudentId(),request.getInternshipId(),StatusRegisterLecturer.APPROVAL );
        if(exists1){
            throw new RuntimeException("Bạn đã có giảng viên phụ trách");
        }
        boolean exists2 = registerLecturerRepository.existsByStudent_UserIdAndInternship_InternshipIdAndStatus(request.getStudentId(),request.getInternshipId(),StatusRegisterLecturer.PENDING );
        if(exists2){
            throw new RuntimeException("Bạn đang có yêu cầu đăng ký giảng viên đang chờ xử lý");
        }
        int pending = registerLecturerRepository
        .countByStudent_UserIdAndInternship_InternshipIdAndStatus(
                request.getStudentId(),
                request.getInternshipId(),
                StatusRegisterLecturer.PENDING);

        int approval = registerLecturerRepository
                .countByStudent_UserIdAndInternship_InternshipIdAndStatus(
                        request.getStudentId(),
                        request.getInternshipId(),
                        StatusRegisterLecturer.APPROVAL); 
        int sum =pending+approval;
        UserInternshipEntity userInternshipEntity = userInternshipRepository.findByUserEntity_UserIdAndInternshipEntity_InternshipId(request.getLecturerId(),request.getInternshipId()).orElseThrow(()->new RuntimeException("Loi"));
        if(sum>userInternshipEntity.getMaxStudent()){
            throw new RuntimeException("Giảng viên đã đủ số lượng sinh viên");
        }
        UserEntity student = userRopository.findById(request.getStudentId()).orElseThrow(()->new RuntimeException("Not found!"));
        UserEntity lecturer = userRopository.findById(request.getLecturerId()).orElseThrow(()->new RuntimeException("Not found!"));
        InternshipEntity internship = internshipRepository.findById(request.getInternshipId()).orElseThrow(()->new RuntimeException("Not found!"));
        RegisterLecturerEntity registerLecturerEntity = RegisterLecturerEntity.builder()
                                .lecturer(lecturer)
                                .student(student)
                                .internship(internship)
                                .status(StatusRegisterLecturer.PENDING)
                                .build();
        return registerLecturerRepository.save(registerLecturerEntity);
    }
    public List<RegisterLecturerEntity> getLecturerByStudentInternship(String userId,String internshipId){
        return registerLecturerRepository.findByStudent_UserIdAndInternship_InternshipId(userId, internshipId);
    }
    public List<RegisterLecturerEntity> getStudentsByLecturersInternship(String userId,String internshipId){
        return registerLecturerRepository.findByLecturer_UserIdAndInternship_InternshipIdAndStatus(userId, internshipId,StatusRegisterLecturer.APPROVAL);
    }
    public long countStudentOfLecturer(String userId,String internshipId){
        return registerLecturerRepository.countByLecturer_UserIdAndInternship_InternshipId(userId,internshipId);
    }
}
