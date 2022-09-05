package com.appcent.cvbuilder.security.auth.firebase.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class FirebaseAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        Map<String, Object> error = new HashMap<>();
        int errorCode = 401;
        error.put("message", "Unauthorized access of protected resource, invalid credentials");
        error.put("error", HttpStatus.UNAUTHORIZED);
        error.put("code", errorCode);
        error.put("timestamp", new Timestamp(new Date().getTime()));
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
