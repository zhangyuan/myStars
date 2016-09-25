package com.evcheung.apps.mystars.controllers;

import com.evcheung.apps.mystars.services.AppConfig;
import com.evcheung.apps.mystars.services.Random;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest extends ControllerTest {
    @MockBean
    private AppConfig appConfig;

    @MockBean
    private Random random;

    @LocalServerPort
    int port;

    @Before
    public void setup() throws Exception {
        given(random.text(10)).willReturn("1234512345");
        given(appConfig.getClientId()).willReturn("fakeClientId");
        given(appConfig.getClientSecret()).willReturn("fakeSecret");
    }

    @Test
    public void should_return_hello_world_when_get_index() throws Exception {
        SimpleResponse simpleResponse = get("/auth/github/connect");
        simpleResponse.status().shouldBe(HttpStatus.FOUND);
        simpleResponse.location().shouldBe("https://github.com/login/oauth/authorize?response_type=code&client_id=fakeClientId&redirect_uri=http%3A%2F%2Flocalhost%3A" + port +"%2Fauth%2Fgithub%2Fcallback&state=1234512345");
    }

}
