package com.evcheung.apps.mystars.controllers;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleLocation {
    private URI location;
    public SimpleLocation(URI location) {
        this.location = location;
    }

    public void shouldBe(String uri) {
        assertThat(this.location.toString()).isEqualTo(uri);
    }
}
