package com.Hiep.B23DCCN295.dto.request;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserRequest {
    
    @Size(min=1)
    private String firstname;
    @Size(min=1)
    private String lastname;
    @Email
    private String email;
    @Size(min = 8)
    private String password;

    private String image;

    private List<String> role;

    private StudentRequest studentRequest;
    
    private LecturerRequest lecturerRequest;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public StudentRequest getStudentRequest() {
        return studentRequest;
    }

    public void setStudentRequest(StudentRequest studentRequest) {
        this.studentRequest = studentRequest;
    }

    public LecturerRequest getLecturerRequest() {
        return lecturerRequest;
    }

    public void setLecturerRequest(LecturerRequest lecturerRequest) {
        this.lecturerRequest = lecturerRequest;
    }

    
}
