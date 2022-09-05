package com.appcent.cvbuilder.security.auth.linkedin.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.appcent.cvbuilder.security.auth.linkedin.model.LinkedinProfile;

@Service
public class LinkedinProviderServiceImpl implements LinkedinProviderService{

    @Autowired
    RestTemplate restTemplate;

    @Value("${linkedin.api.baseUrl}")
    private String baseUrl;
    @Value("${linkedin.api.oauth.url}")
    private String oauthUrl;
    private String emailUrl = "emailAddress?q=members&projection=(elements*(handle~))";
    private String profileUrl = "me";

    @Value("${linkedin.api.client.id}")
    private String clientId;
    @Value("${linkedin.api.client.secret}")
    private String clientSecret;
    @Value("${linkedin.api.client.callback.url}")
    private String callbackUrl;

    @Override
    public String getEmail(String token) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> result = restTemplate.exchange(baseUrl+emailUrl, HttpMethod.GET, entity, String.class, "");
        Object responseBody = result.getBody();
        JSONObject response = new JSONObject((String) responseBody);
        return response.getJSONArray("elements").getJSONObject(0).getJSONObject("handle~").getString("emailAddress");
    }

    @Override
    public LinkedinProfile getProfile(String token) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> result = restTemplate.exchange(baseUrl+profileUrl, HttpMethod.GET, entity, String.class, "");
        Object responseBody = result.getBody();
        JSONObject response = new JSONObject((String) responseBody);
        LinkedinProfile profile = LinkedinProfile.builder()
            .name(response.getString("localizedFirstName"))
            .email(getEmail(token))
            .build();
        return profile;
    }

    @Override
    public String getAccessToken(String code) throws JSONException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(oauthUrl)
        .queryParam("client_id",clientId)
        .queryParam("client_secret",clientSecret)
        .queryParam("grant_type","authorization_code")
        .queryParam("redirect_uri",callbackUrl)
        .queryParam("code",code);

        ResponseEntity<String> result = restTemplate.postForEntity(builder.build().toUri(), null , String.class );
        Object responseBody = result.getBody();
        JSONObject response = new JSONObject((String) responseBody);
        return response.getString("access_token");
    }
    
}
