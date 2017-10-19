package com.nearsoft.slackbot.fortunebot.domain;

public class BotAuthInfo {
    private final String id;
    private final String accessToken;

    public BotAuthInfo(String id, String accessToken) {
        this.id = id;
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
