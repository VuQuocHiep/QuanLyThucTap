package com.Hiep.B23DCCN295.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Lecturer {

    private String degree;

    private String department;

    private String experience;

    public Lecturer() {
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}