package com.evcheung.apps.mystars.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StarsControllerTest  extends ControllerTest{
    @Test
    public void should_return_ok_when_get_stars() throws Exception {
        SimpleResponse simpleResponse = get("/starred");
        simpleResponse.status().shouldBeOk();
        simpleResponse.jsonPath("$").shouldBeArray();
    }

    @Test
    public void should_return_201_and_star_when_create_star() throws Exception {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("tags", Arrays.asList("ReactJS", "Redux"));
        SimpleResponse simpleResponse = put("/starred/reactjs/redux", payload);

        simpleResponse.status().shouldBeOk();
        simpleResponse.jsonPath("owner").shouldEqual("reactjs");
        simpleResponse.jsonPath("repositoryName").shouldEqual("redux");
        simpleResponse.jsonPath("tags").shouldBeArray();
        simpleResponse.jsonPath("tags[0]").shouldEqual("ReactJS");
        simpleResponse.jsonPath("tags[1]").shouldEqual("Redux");
    }

    @Test
    public void should_return_created_stars_when_get_stars() throws Exception {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("tags", Arrays.asList("ReactJS", "Redux"));
        put("/starred/reactjs/redux", payload);

        SimpleResponse simpleResponse = get("/starred");

        simpleResponse.status().shouldBeOk();
        simpleResponse.jsonPath("$.length()").shouldEqual(1);
    }
}
