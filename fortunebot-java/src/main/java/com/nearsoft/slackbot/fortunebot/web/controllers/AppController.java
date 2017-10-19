package com.nearsoft.slackbot.fortunebot.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/install")
    public String install() {
        return "install";
    }

    @GetMapping("/thanks")
    public String thanks() {
        return "thanks";
    }

}
