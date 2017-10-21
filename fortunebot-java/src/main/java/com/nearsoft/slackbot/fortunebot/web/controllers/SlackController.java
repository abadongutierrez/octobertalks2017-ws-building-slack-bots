package com.nearsoft.slackbot.fortunebot.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SlackController {

    @PostMapping(value = "/slack", produces = MediaType.TEXT_PLAIN_VALUE)
    public String slack(@RequestBody String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(body);

        // TODO: if event in body is a challenge with type=url_verification return the value of the "challenge" attribute

        return "Ok";
    }
}
