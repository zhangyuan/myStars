package com.evcheung.apps.mystars.controllers;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SimpleResponse {
    private final DocumentContext context;
    private final HttpStatus statusCode;
    private final HttpHeaders headers;

    public String getBody() {
        return body;
    }

    private final String body;

    public SimpleResponse(ResponseEntity<String> responseEntity) {
        statusCode = responseEntity.getStatusCode();
        body = responseEntity.getBody();
        context = body != null ? JsonPath.parse(body) : null;
        headers = responseEntity.getHeaders();
    }

    public SimpleJsonPath jsonPath(String path){
        return new SimpleJsonPath(context, path);
    }

    public SimpleStatusCode status() {
        return new SimpleStatusCode(statusCode);
    }

    public SimpleLocation location() {
        return new SimpleLocation(headers.getLocation());
    }
}
