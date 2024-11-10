package com.raslen.StackOverflow.services.user;
import  com.raslen.StackOverflow.dtos.SignupRequest;
import com.raslen.StackOverflow.dtos.UserDTO;

public interface UserService {

UserDTO createUser(SignupRequest signupRequest) throws Exception;

boolean hasUserWithEmail(String email);

}
