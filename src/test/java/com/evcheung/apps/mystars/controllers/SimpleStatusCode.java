package com.evcheung.apps.mystars.controllers;

import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleStatusCode {
    private HttpStatus statusCode;

    public SimpleStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public void shouldBeOk() {
        assertThat(statusCode).isEqualTo(HttpStatus.OK);
    }
}
