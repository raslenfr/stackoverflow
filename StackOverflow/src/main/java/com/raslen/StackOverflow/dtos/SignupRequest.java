package com.raslen.StackOverflow.dtos;

import lombok.Data;

@Data
public class SignupRequest {

    private long id ;
    private String name;
    private String email;
    private String password ;

}
