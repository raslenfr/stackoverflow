package com.raslen.StackOverflow.filter;

import com.raslen.StackOverflow.Utils.JwtUtil;
import com.raslen.StackOverflow.services.jwt.UserDetailsServiceimpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private final UserDetailsServiceimpl userDetailsServiceimpl;


    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UserDetailsServiceimpl userDetailsServiceimpl, JwtUtil jwtUtil) {
        this.userDetailsServiceimpl = userDetailsServiceimpl;
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     String authHeader = request.getHeader("Authorization");
     String token = null;
     String username = null;

     if (authHeader != null && authHeader.startsWith("bearer")){
         token = authHeader.substring(7);
         username = jwtUtil.extractUsername(token);
     }
     if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
         UserDetails userDetails = userDetailsServiceimpl.loadUserByUsername(username);

      if(jwtUtil.validateToken(token,userDetails)){
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
     }
     filterChain.doFilter(request,response);
    }
}
