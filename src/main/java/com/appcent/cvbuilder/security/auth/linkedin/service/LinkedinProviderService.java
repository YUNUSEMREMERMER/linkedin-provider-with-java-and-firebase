package com.appcent.cvbuilder.security.auth.linkedin.service;

import org.json.JSONException;

import com.appcent.cvbuilder.security.auth.linkedin.model.LinkedinProfile;

public interface LinkedinProviderService {
    String getEmail(String token) throws JSONException;
    LinkedinProfile getProfile(String token) throws JSONException;
    String getAccessToken(String code) throws JSONException;
}
