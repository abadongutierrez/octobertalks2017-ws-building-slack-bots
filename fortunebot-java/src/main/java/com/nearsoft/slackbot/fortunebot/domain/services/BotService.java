package com.nearsoft.slackbot.fortunebot.domain.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.slackbot.fortunebot.domain.BotAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BotService {

    @Autowired
    private LocalDbService localDbService;

    public void auth(String code) throws IOException {
        Map<String, String> uriVars = new HashMap<>();
        uriVars.put("client_id", System.getenv("CLIENT_ID"));
        uriVars.put("client_secret", System.getenv("CLIENT_SECRET"));
        uriVars.put("code", code);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("https://slack.com/api/oauth.access?" +
                        "client_id={client_id}&client_secret={client_secret}&code={code}", String.class, uriVars);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseEntity.getBody());
        JsonNode botNode = root.path("bot");
        localDbService.setBotAuthInfo(buildBotAuthInfo(botNode));
    }

    private BotAuthInfo buildBotAuthInfo(JsonNode botNode) {
        return new BotAuthInfo(botNode.path("bot_user_id").asText(), botNode.path("bot_access_token").asText());
    }

}
