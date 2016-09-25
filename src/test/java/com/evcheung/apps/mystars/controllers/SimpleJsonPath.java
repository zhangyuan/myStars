package com.evcheung.apps.mystars.controllers;

import com.jayway.jsonpath.DocumentContext;
import org.hamcrest.core.IsInstanceOf;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleJsonPath {
    private DocumentContext context;
    private String path;

    public SimpleJsonPath(DocumentContext context, String path) {
        this.context = context;
        this.path = path;
    }

    public void shouldBeArray() {
        assertThat(context.read(path), IsInstanceOf.instanceOf(List.class));
    }

    public void shouldEqual(Object value) {
        assertThat(context.read(path), is(value));
    }
}
