package com.appcent.cvbuilder.security.auth.linkedin.model;

import lombok.Data;

@Data
@lombok.Builder
public class LinkedinProfile {
    private String name;
    private String email;
}
