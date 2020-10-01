package com.aredzo.mtracker.client;

import com.aredzo.mtracker.dto.sso.TokenResponse;
import com.aredzo.mtracker.dto.sso.UserPostRequest;
import com.aredzo.mtracker.dto.sso.UserTokenResponse;
import com.aredzo.mtracker.dto.sso.ValidateTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
public class SsoClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String ssoServiceUrl;
    private static final Logger log = LoggerFactory.getLogger(SsoClient.class);

    private static final String SSO_PATH_SIGNUP_SERVICE = "/sso/v1/int/users/signup";
    private static final String SSO_PATH_LOGIN = "/sso/v1/users/login";
    private static final String SSO_PATH_VALIDATE_TOKEN = "/sso/v1/int/token";

    public SsoClient(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${sso.service.url}") String ssoServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.ssoServiceUrl = ssoServiceUrl;
    }


    public UserTokenResponse signup(String email, String password) throws HttpStatusCodeException {
        HttpEntity<Object> signupRequest = new HttpEntity<>(new UserPostRequest(email, password));
        String url = UriComponentsBuilder
                .fromUriString(ssoServiceUrl + SSO_PATH_SIGNUP_SERVICE)
                .toUriString();
        UserTokenResponse userTokenResponse = restTemplate.exchange(url, HttpMethod.POST, signupRequest, UserTokenResponse.class).getBody();
        log.info("Signed up as service: {}", userTokenResponse);
        return userTokenResponse;
    }

    public TokenResponse login(String email, String password) throws HttpStatusCodeException {
        HttpEntity<Object> loginRequest = new HttpEntity<>(new UserPostRequest(email, password));
        String url = UriComponentsBuilder
                .fromUriString(ssoServiceUrl + SSO_PATH_LOGIN)
                .toUriString();

        TokenResponse tokenResponse = restTemplate.exchange(url, HttpMethod.POST, loginRequest, TokenResponse.class).getBody();
        log.info("Login successful: {}", tokenResponse);
        return tokenResponse;
    }

    public ValidateTokenResponse validateToken(UUID appToken, UUID userToken) throws HttpStatusCodeException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", appToken.toString());

        HttpEntity<Object> validateTokenRequest = new HttpEntity<>(null, headers);
        String url = UriComponentsBuilder
                .fromUriString(ssoServiceUrl + SSO_PATH_VALIDATE_TOKEN)
                .queryParam("token", userToken.toString())
                .toUriString();
        ValidateTokenResponse validateTokenResponse = restTemplate.exchange(url, HttpMethod.GET, validateTokenRequest, ValidateTokenResponse.class).getBody();
        log.info("Validated token: {}", validateTokenResponse);
        return validateTokenResponse;
    }
}
