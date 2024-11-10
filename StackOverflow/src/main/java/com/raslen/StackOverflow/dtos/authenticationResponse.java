package com.raslen.StackOverflow.dtos;


public class authenticationResponse {
    private String jwtToken ;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public authenticationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
