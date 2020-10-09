package com.aredzo.mtracker.sso.client.contract;


import com.aredzo.mtracker.client.SsoClient;
import com.aredzo.mtracker.dto.sso.UserTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "com.aredzo:mtracker-sso-external-contracts:+:stubs")
public class SsoClientContractTest {

    @StubRunnerPort("mtracker-sso-external-contracts")
    int stubPort;

    private SsoClient ssoClient;

    @Before
    public void setUp() throws Exception {
        ssoClient = new SsoClient(new RestTemplate(), new ObjectMapper(), "http://localhost:" + stubPort);
    }

    @Test
    public void validSignupTest() {
        String email = "foo@bar.com";
        UserTokenResponse response = ssoClient.signup(email, "somePassword");

        assertThat(response.getEmail()).isNotEmpty();
        assertThat(response.getUserId()).isPositive();
        assertThat(response.getToken()).isNotNull();
    }

    @Test(expected = HttpClientErrorException.class)
    public void invalidSignupEmptyEmailFieldTest() {
        ssoClient.signup("", "somePassword");
    }

    @Test(expected = HttpClientErrorException.class)
    public void invalidSignupEmptyPasswordFieldTest() {
        ssoClient.signup("foo@bar.com", "");
    }

}
