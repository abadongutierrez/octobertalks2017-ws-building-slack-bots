package com.nearsoft.slackbot.fortunebot.domain.services;

import com.nearsoft.slackbot.fortunebot.domain.BotAuthInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LocalDbService {
    private final Map<String, Object> map = new HashMap<>();

    public void setBotAuthInfo(BotAuthInfo botAuthInfo) {
        map.put("bot-info", botAuthInfo);
    }

    public BotAuthInfo getBotAuthInfo() {
        return (BotAuthInfo) map.get("bot-info");
    }
}
