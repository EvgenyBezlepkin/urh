package com.example.demo.event;

import com.example.demo.domain.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class PasswordActionsEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;
    private EventType eventType;

    public PasswordActionsEvent(User user, Locale locale, String appUrl, EventType eventType) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.eventType = eventType;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
