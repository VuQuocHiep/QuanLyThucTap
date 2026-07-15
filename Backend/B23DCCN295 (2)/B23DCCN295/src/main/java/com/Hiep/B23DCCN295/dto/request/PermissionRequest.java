package com.Hiep.B23DCCN295.dto.request;

import jakarta.validation.constraints.NotNull;

public class PermissionRequest {
    
    @NotNull
    private String name;
    
    private String description;

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

    
}
