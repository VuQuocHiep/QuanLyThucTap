package com.Hiep.B23DCCN295.dto.response;

public class AuthenticationResponse {
    
    private boolean authentication;
    private String token;
    public boolean isAuthentication() {
        return authentication;
    }
    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    
}
