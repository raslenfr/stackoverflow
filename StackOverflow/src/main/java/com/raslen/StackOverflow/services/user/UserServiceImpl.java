package com.raslen.StackOverflow.services.user;

import com.raslen.StackOverflow.dtos.SignupRequest;
import com.raslen.StackOverflow.dtos.UserDTO;
import com.raslen.StackOverflow.entities.User;
import com.raslen.StackOverflow.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public UserDTO createUser(SignupRequest signupRequest) throws Exception {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        User createdUser = userRepository.save(user);

        UserDTO createdUserDTO = new UserDTO();
        createdUserDTO.setId(createdUser.getId());
        createdUserDTO.setName(createdUser.getName());
        createdUserDTO.setEmail(createdUser.getEmail());

        return createdUserDTO;
    }



    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
