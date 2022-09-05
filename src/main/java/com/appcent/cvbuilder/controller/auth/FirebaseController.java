package com.appcent.cvbuilder.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appcent.cvbuilder.security.auth.firebase.model.User;
import com.appcent.cvbuilder.security.auth.firebase.service.FirebaseSecurityService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class FirebaseController {

    private final FirebaseSecurityService firebaseSecurityService;

    @GetMapping("/getUser")
    public ResponseEntity<?> getUserDetails() {
        User user = firebaseSecurityService.getUser();
        return ResponseEntity.ok().body(user);
    }

}
