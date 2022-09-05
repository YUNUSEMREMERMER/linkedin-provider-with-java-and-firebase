package com.appcent.cvbuilder.security.auth.firebase.service;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import com.appcent.cvbuilder.security.auth.firebase.model.JwtResponse;
import com.appcent.cvbuilder.security.auth.firebase.model.User;
import com.appcent.cvbuilder.security.auth.linkedin.model.LinkedinProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

@Service
@AllArgsConstructor
public class FirebaseSecurityServiceImpl implements FirebaseSecurityService{
    
    @Override
    public User getUser() {
        User userPrincipal = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof User) {
            userPrincipal = ((User) principal);
        }
        return userPrincipal;
    }

    @Override
    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }

    @Override
    public Boolean emailIsExist(String email) throws FirebaseAuthException {
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
        } catch (FirebaseAuthException e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean isUserExistFromUid(String uid) throws FirebaseAuthException {
        return true;
    }

    @Override
    public void createUserFromUid(String uid) throws FirebaseAuthException {
        createTokenWithUid(String.valueOf(uid));
        System.out.println("uid: " + String.valueOf(uid));
    }

    @Override
    public JwtResponse createUserWithLinkedinProvider(LinkedinProfile profile) throws FirebaseAuthException {
        UserRecord userRecord;
        if(emailIsExist(profile.getEmail()) == false){
            CreateRequest request = new CreateRequest()
            .setEmail(profile.getEmail())
            .setEmailVerified(false)
            .setPassword(UUID.randomUUID().toString())
            .setDisplayName(profile.getName())
            .setDisabled(false);
            userRecord = FirebaseAuth.getInstance().createUser(request);
        }else{
            userRecord = FirebaseAuth.getInstance().getUserByEmail(profile.getEmail());
        }
        JwtResponse response = createTokenWithUid(userRecord.getUid());
        response.setEmail(profile.getEmail());
        return response;
    }

    @Override
    public JwtResponse createTokenWithUid(String uid) throws FirebaseAuthException {
        String jwt = FirebaseAuth.getInstance().createCustomToken(uid).toString();
        return JwtResponse.builder()
            .token(jwt)
            .build();
    }

    

    

    
}
