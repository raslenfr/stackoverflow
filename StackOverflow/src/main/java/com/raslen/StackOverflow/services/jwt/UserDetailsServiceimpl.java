package com.raslen.StackOverflow.services.jwt;

import com.raslen.StackOverflow.entities.User;
import com.raslen.StackOverflow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {

@Autowired
private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       //write our logic to get user from db//

        Optional<User> userOptional = userRepository.findFirstByEmail(email);
       if(userOptional.isEmpty())
           throw new UsernameNotFoundException("User not found !");
        return new org.springframework.security.core.userdetails.User(userOptional.get().getEmail(),userOptional.get().getPassword(), new ArrayList<>());
    }
}
