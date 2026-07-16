package com.Hiep.B23DCCN295.dto.request;

import java.util.List;

import jakarta.validation.constraints.Size;

public class RoleRequest {
    
    @Size(min = 1)
    private String name;
    private String description;
    private List<String> permission;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<String> getPermission() {
        return permission;
    }
    public void setPermission(List<String> permission) {
        this.permission = permission;
    }
    
}
