package com.raslen.StackOverflow.controller;

import com.raslen.StackOverflow.dtos.SignupRequest;
import com.raslen.StackOverflow.dtos.UserDTO;
import com.raslen.StackOverflow.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody(required = true) SignupRequest signupRequest) throws Exception {

        if (userService.hasUserWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("user already exists with this " + signupRequest.getEmail(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserDTO createdUser = userService.createUser(signupRequest); // Use signupRequest instead of signupDTO
        if (createdUser == null) {
            return new ResponseEntity<>("user not created, come again later!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
