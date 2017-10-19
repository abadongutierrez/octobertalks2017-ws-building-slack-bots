package com.nearsoft.slackbot.fortunebot.domain.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nearsoft.slackbot.fortunebot.domain.BotAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
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

    public void handleEvent(SlackEvent event) {
        if (localDbService.getBotAuthInfo() != null &&
                event.isMessageType() &&
                !event.isTextEmpty() &&
                !isEventFromBotUser(event)) {
            echoText(event);
        }
    }

    private boolean isEventFromBotUser(SlackEvent event) {
        return StringUtils.isEmpty(event.getUser()) || event.getUser().equals(localDbService.getBotAuthInfo().getId());
    }

    private void echoText(SlackEvent event) {
        postMessage(event.getChannel(), event.getText());
    }

    private void postMessage(String channel, String text) {
        if (localDbService.getBotAuthInfo() != null ) {
            RestTemplate chatRestTemplate = new RestTemplate();
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("token", localDbService.getBotAuthInfo().getAccessToken());
            parts.add("channel", channel);
            parts.add("text", text);
            chatRestTemplate.postForObject("https://slack.com/api/chat.postMessage", parts, String.class);
        }
    }

}
