package com.Hiep.B23DCCN295.dto.request;

import jakarta.persistence.Column;

public class StudentRequest {
    
    private String mssv;

    private String className;

    private Boolean leader;

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }
    
}
