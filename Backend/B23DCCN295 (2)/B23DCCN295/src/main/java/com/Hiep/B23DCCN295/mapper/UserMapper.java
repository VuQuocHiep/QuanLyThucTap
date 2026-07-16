package com.Hiep.B23DCCN295.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.Hiep.B23DCCN295.dto.request.UserRequest;
import com.Hiep.B23DCCN295.entity.Lecturer;
import com.Hiep.B23DCCN295.entity.RoleEntity;
import com.Hiep.B23DCCN295.entity.Student;
import com.Hiep.B23DCCN295.entity.UserEntity;
import com.Hiep.B23DCCN295.repository.RoleRepository;

@Component
public class UserMapper {
    
    @Autowired
    private RoleRepository roleRepository;

    public UserEntity toEntity(UserRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstname(request.getFirstname());
        userEntity.setLastname(request.getLastname());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setImage(request.getImage());
        Set<RoleEntity> role = new HashSet<>();
        for(String x:request.getRole()){
            RoleEntity s = roleRepository.findByName(x).orElseThrow(() -> new RuntimeException("Not found!"));;
            role.add(s);
        }
        userEntity.setRole(role);
        if(request.getLecturerRequest()!=null){
            Lecturer lecturer = new Lecturer();
            lecturer.setDegree(request.getLecturerRequest().getDegree());
            lecturer.setDepartment(request.getLecturerRequest().getDepartment());
            lecturer.setExperience(request.getLecturerRequest().getExperience());
            userEntity.setLecturer(lecturer);
        }
        else{
            Student student = new Student();
            student.setClassName(request.getStudentRequest().getClassName());
            student.setMssv(request.getStudentRequest().getMssv());
            student.setLeader(request.getStudentRequest().getLeader());
            userEntity.setStudent(student);
        }
        return userEntity;
    }
}
