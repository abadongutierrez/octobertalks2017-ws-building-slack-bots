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
        // TODO: put in uriVars map "client_id" with the value of the CLIENT_ID environment variable
        // TODO: put in uriVars map "client_secret" with the value of the CLIENT_SECRET environment variable
        uriVars.put("code", code);

        // TODO: call GET "https://slack.com/api/oauth.access"
        // TIP: use ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables)
        // TIP: Remember that when sending Query params they go in the format: param={param_value}
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseEntity.getBody());
        JsonNode botNode = root.path("bot");
        localDbService.setBotAuthInfo(buildBotAuthInfo(botNode));
    }

    private BotAuthInfo buildBotAuthInfo(JsonNode botNode) {
        return new BotAuthInfo(botNode.path("bot_user_id").asText(), botNode.path("bot_access_token").asText());
    }

}
