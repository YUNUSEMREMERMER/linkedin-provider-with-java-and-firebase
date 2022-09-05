package com.appcent.cvbuilder.controller.auth;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appcent.cvbuilder.security.auth.firebase.service.FirebaseSecurityService;
import com.appcent.cvbuilder.security.auth.linkedin.model.LinkedinProfile;
import com.appcent.cvbuilder.security.auth.linkedin.service.LinkedinProviderService;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class LinkedinProviderController {
    
    private final LinkedinProviderService linkedinProviderService;
    private final FirebaseSecurityService firebaseSecurityService;
    
    @GetMapping("/linkedinCallback")
    public ResponseEntity<?> linkedinCallback(@RequestParam String code) throws JSONException, FirebaseAuthException{
        String token = linkedinProviderService.getAccessToken(code);
        LinkedinProfile profile = linkedinProviderService.getProfile(token);
        var result = firebaseSecurityService.createUserWithLinkedinProvider(profile);
        if(result!=null){
            return ResponseEntity.ok().body(result);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Linkedin ile giris islemi basarisiz");
        }
    }

}
