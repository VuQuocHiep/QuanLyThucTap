package com.Hiep.B23DCCN295.dto.response;

public class AuthenticationResponse {
    
    private boolean authentication;
    private String token;
    private String refreshToken;

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
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
}
