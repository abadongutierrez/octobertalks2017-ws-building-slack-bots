package com.nearsoft.slackbot.fortunebot.domain.services;

import org.springframework.util.StringUtils;

public class SlackEvent {
    private final String type;
    private final String user;
    private final String channel;
    private final String text;

    public SlackEvent(String type, String user, String channel, String text) {
        this.type = type;
        this.user = user;
        this.channel = channel;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getChannel() {
        return channel;
    }

    public boolean isMessageType() {
        return "message".equals(type);
    }

    public boolean isTextEmpty() {
        return StringUtils.isEmpty(text);
    }
}
