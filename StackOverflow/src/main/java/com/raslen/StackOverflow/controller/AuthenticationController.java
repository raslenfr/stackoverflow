package com.raslen.StackOverflow.controller;

import com.raslen.StackOverflow.Utils.JwtUtil;
import com.raslen.StackOverflow.dtos.AuthenticationResquest;
import com.raslen.StackOverflow.dtos.authenticationResponse;
import com.raslen.StackOverflow.entities.User;
import com.raslen.StackOverflow.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;
    public static final String TOKEN_PREFIX ="Bearer";
    public static final String HEADER_STRING ="Authorization";

@PostMapping("/authentication")
public void  createAuthenticationToken(@RequestBody AuthenticationResquest authenticationResquest, HttpServletResponse response) throws IOException, JSONException {
    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationResquest.getEmail(), authenticationResquest.getPassword())
        );
    } catch (BadCredentialsException e) {
        throw new BadCredentialsException("Incorrect Email or Password. Please check again.");
    } catch (DisabledException disabledException) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created");
        return ;
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationResquest.getEmail());
    Optional<User> OptionalUser= userRepository.findFirstByEmail(userDetails.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails.getUsername());

    if (OptionalUser.isPresent()){
       response.getWriter().write(new JSONObject()
               .put("userId",OptionalUser.get().getId())
               .toString()
       );
    }

    response.addHeader("Access-Control-Expose-Headers", "Authorization");
    response.setHeader("access-Control-Allow-Headers" ,"Authorization,X-PINGOTHER, X-Requested-With, Content-Type, Accept,X-Custom-header");
    response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwt );


}}
