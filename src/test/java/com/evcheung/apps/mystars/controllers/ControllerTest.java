package com.evcheung.apps.mystars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    protected SimpleResponse get(String uri) {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(uri, String.class);

        return new SimpleResponse(responseEntity);
    }

    protected SimpleResponse put(String path, Object payload) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(payload);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(path, HttpMethod.PUT, requestEntity, String.class);

        return new SimpleResponse(responseEntity);
    }
}
