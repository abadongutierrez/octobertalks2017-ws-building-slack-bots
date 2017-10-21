package com.nearsoft.slackbot.fortunebot.web.controllers;

import com.nearsoft.slackbot.fortunebot.domain.services.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Controller
public class AppController {

    @Autowired
    private BotService botService;

    @GetMapping("/install")
    public ModelAndView install(Map<String, Object> model) {
        // TODO: put 'client_id' in the model map with the value of the CLIENT_ID environment variable
        return new ModelAndView("install", model);
    }

    @GetMapping("/thanks")
    public String thanks(@RequestParam String code) throws IOException {
        botService.auth(code);
        return "thanks";
    }

}
