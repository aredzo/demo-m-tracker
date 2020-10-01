package com.aredzo.mtracker.service;

import com.aredzo.mtracker.client.SsoClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SsoClientService {

    private final SsoClient ssoClient;

    public SsoClientService(SsoClient ssoClient) {
        this.ssoClient = ssoClient;
    }

    public Integer validateTokenAndGetUserId(UUID token){
        return -1;
    }
}
