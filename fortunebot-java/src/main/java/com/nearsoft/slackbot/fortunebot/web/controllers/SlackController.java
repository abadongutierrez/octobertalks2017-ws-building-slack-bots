package com.nearsoft.slackbot.fortunebot.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlackController {

    @PostMapping(value = "/slack", produces = MediaType.TEXT_PLAIN_VALUE)
    public String slack() {
        return "Ok";
    }
}
