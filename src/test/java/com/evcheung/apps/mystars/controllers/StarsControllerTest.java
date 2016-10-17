package com.evcheung.apps.mystars.controllers;

import com.evcheung.apps.mystars.providers.GitHubProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StarsControllerTest  extends ControllerTest{
    @Autowired
    GitHubProvider githubProvider;

    @Test
    public void should_return_ok_when_get_stars() throws Exception {
        SimpleResponse simpleResponse = get("/starred");
        simpleResponse.status().shouldBeOk();
        simpleResponse.jsonPath("$").shouldBeArray();
    }

    @Test
    public void should_return_ok_and_created_start_after_creating_star() throws Exception {
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
        simpleResponse.jsonPath("$[0].owner").shouldEqual("reactjs");
        simpleResponse.jsonPath("$[0].repositoryName").shouldEqual("redux");
        simpleResponse.jsonPath("$[0].tags").shouldBeArray();
        simpleResponse.jsonPath("$[0].tags[0]").shouldEqual("ReactJS");
        simpleResponse.jsonPath("$[0].tags[1]").shouldEqual("Redux");
    }

    @Test
    public void should_update_star_given_same_owner_and_repository_name_when_get_stars() throws Exception {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("tags", Arrays.asList("ReactJS", "Redux"));
        put("/starred/reactjs/redux", payload);

        payload = new HashMap<>();
        payload.put("tags", Arrays.asList("ReactJS", "JavaScript"));
        put("/starred/reactjs/redux", payload);

        SimpleResponse simpleResponse = get("/starred");

        simpleResponse.status().shouldBeOk();
        simpleResponse.jsonPath("$.length()").shouldEqual(1);
        simpleResponse.jsonPath("$[0].owner").shouldEqual("reactjs");
        simpleResponse.jsonPath("$[0].repositoryName").shouldEqual("redux");
        simpleResponse.jsonPath("$[0].tags").shouldBeArray();
        simpleResponse.jsonPath("$[0].tags[0]").shouldEqual("ReactJS");
        simpleResponse.jsonPath("$[0].tags[1]").shouldEqual("JavaScript");
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);

    @Test
    public void should_sync_stars_from_github() throws Exception {
        githubProvider.setApiPrefix("http://localhost:9090");

        GitHubStarred gitHubStarred = new GitHubStarred();
        gitHubStarred.setName("myStars");
        gitHubStarred.setDescription("manage your GitHub stars");
        gitHubStarred.setUrl("https://github.com/zhangyuan/myStars");
        GitHubOwner owner = new GitHubOwner();
        owner.setLogin("zhangyuan");
        gitHubStarred.setOwner(owner);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(Arrays.asList(gitHubStarred));

        stubFor(WireMock.get(WireMock.urlEqualTo("/user/zhangyuan/starred"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(json)));

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("username", "zhangyuan");
        SimpleResponse simpleResponse = put("/starred/sync", payload);
        simpleResponse.status().shouldBeOk();
    }
}
