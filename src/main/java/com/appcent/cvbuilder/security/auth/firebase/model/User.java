package com.appcent.cvbuilder.security.auth.firebase.model;

import lombok.Data;

@Data
public class User {
	private String uid;
	private String name;
	private String email;
	private boolean isEmailVerified;
	private String issuer;
	private String picture;
}
