package com.evcheung.apps.mystars.controllers;

import com.evcheung.apps.mystars.services.OAuthService;
import com.evcheung.apps.mystars.services.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    OAuthService oAuthService;

    @Autowired
    Random random;

    @RequestMapping(path = "github/connect",method = RequestMethod.GET)
    public ResponseEntity connect() throws URISyntaxException {
        UriComponents uriComponents = oAuthService.getCallback();

        URI uri = new URI(oAuthService.authorizeUrl(uriComponents.toUriString(), random.text(10)));
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    @RequestMapping(path = "github/callback",method = RequestMethod.GET)
    public ResponseEntity callback(@RequestParam String code, @RequestParam String state) throws URISyntaxException, IOException {
        String accessToken = oAuthService.authorize(code, oAuthService.getCallback().toString());
        HashMap<String, String> response = new HashMap<>();
        response.put("githubAccessToken", accessToken);
        return ResponseEntity.ok(response);
    }
}
