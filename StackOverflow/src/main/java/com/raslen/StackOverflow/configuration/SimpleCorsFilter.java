package com.raslen.StackOverflow.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

    private final String clientAppUrl = "http://localhost:4200";  // Correct URL without wildcard

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // CORS Headers
        String originHeader = request.getHeader("Origin");  // Get the origin of the request
        if (clientAppUrl.equals(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
        } else {
            response.setHeader("Access-Control-Allow-Origin", clientAppUrl);  // Default to localhost:4200
        }

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");  // Corrected capitalization
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Credentials", "true");  // Allow credentials if needed

        // Handle preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(req, res);  // Proceed with the next filters
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize filter if needed
    }

    @Override
    public void destroy() {
        // Cleanup filter if needed
    }
}
