package com.appcent.cvbuilder.security.auth.firebase.service;

import javax.servlet.http.HttpServletRequest;

import com.appcent.cvbuilder.security.auth.firebase.model.JwtResponse;
import com.appcent.cvbuilder.security.auth.firebase.model.User;
import com.appcent.cvbuilder.security.auth.linkedin.model.LinkedinProfile;
import com.google.firebase.auth.FirebaseAuthException;

public interface FirebaseSecurityService {
    User getUser();
    String getBearerToken(HttpServletRequest request);
    Boolean emailIsExist(String email) throws FirebaseAuthException;
    Boolean isUserExistFromUid(String uid) throws FirebaseAuthException;
    void createUserFromUid(String uid) throws FirebaseAuthException;
    JwtResponse createUserWithLinkedinProvider(LinkedinProfile linkedinProfile) throws FirebaseAuthException;
    JwtResponse createTokenWithUid(String uid) throws FirebaseAuthException;
}
