package com.evcheung.apps.mystars.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class Random {
    public String text(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
