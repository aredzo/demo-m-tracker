package com.aredzo.mtracker.service;

import com.aredzo.mtracker.client.SsoClient;
import com.aredzo.mtracker.exception.MTrackerServiceError;
import com.aredzo.mtracker.exception.MTrackerServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;

@Service
public class SsoClientService {

    private final SsoClient ssoClient;
    private final String appSsoEmail;
    private final String appSsoPassword;
    private UUID serviceToken;

    public SsoClientService(
            SsoClient ssoClient,
            @Value("${app.sso.email}") String appSsoEmail,
            @Value("${app.sso.password}") String appSsoPassword) {
        this.ssoClient = ssoClient;
        this.appSsoEmail = appSsoEmail;
        this.appSsoPassword = appSsoPassword;
    }

    public Integer validateTokenAndGetUserId(UUID token) {
        try {
            return ssoClient.validateToken(getServiceToken(), token).getUserId();
        }catch (HttpStatusCodeException e){
            MTrackerServiceError error = MTrackerServiceError.fromString(e.getStatusCode().getReasonPhrase());
            if(error == null){
                throw new MTrackerServiceException(e, MTrackerServiceError.INTERNAL_ERROR);
            }else{
                switch (error){
                    case TOKEN_NOT_FOUND:
                    case USER_NOT_AUTHORIZED:{
                        throw new MTrackerServiceException(MTrackerServiceError.NOT_AUTHORIZED);
                    }
                    case SERVICE_NOT_AUTHORIZED:
                    case SERVICE_TOKEN_NOT_FOUND: {
                        serviceToken = null;
                        return ssoClient.validateToken(getServiceToken(), token).getUserId();
                    }
                    default:{
                        throw new MTrackerServiceException(e, MTrackerServiceError.INTERNAL_ERROR);
                    }
                }
            }
        }
    }


    public UUID getServiceToken() {
        if(serviceToken == null) {
            try {
                return ssoClient.login(appSsoEmail, appSsoPassword).getToken();
            } catch (HttpStatusCodeException e) {
                if (e.getStatusCode().value() == 404) {
                    //If user not found then signup
                    try {
                        return ssoClient.signup(appSsoEmail, appSsoPassword).getToken();
                    } catch (HttpStatusCodeException e1) {
                        throw new MTrackerServiceException(e1, MTrackerServiceError.INTERNAL_ERROR);
                    }
                } else {
                    throw new MTrackerServiceException(e, MTrackerServiceError.INTERNAL_ERROR);
                }
            }
        }else {
            return serviceToken;
        }
    }
}
