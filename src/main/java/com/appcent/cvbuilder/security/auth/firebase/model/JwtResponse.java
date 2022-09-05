package com.appcent.cvbuilder.security.auth.firebase.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    String email;
    String token;
}
