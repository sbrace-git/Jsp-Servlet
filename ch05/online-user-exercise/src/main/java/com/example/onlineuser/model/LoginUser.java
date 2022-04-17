package com.example.onlineuser.model;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.time.Instant;
import java.time.ZoneOffset;

public class LoginUser implements HttpSessionBindingListener {
    private String name;
    private String userAgent;
    private HttpSession httpSession;

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String showDetail() {
        String lastAccessedTimeStr = "";
        String sessionId = "";
        if (null != httpSession) {
            lastAccessedTimeStr = Instant.ofEpochMilli(httpSession.getLastAccessedTime())
                    .atZone(ZoneOffset.systemDefault()).toLocalDateTime().toString();
            sessionId = httpSession.getId();
        }
        return String.format("%s\t%s\t%s\t%s\t%s",
                name, sessionId, lastAccessedTimeStr, data, userAgent);
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        LoginUser value = (LoginUser) event.getValue();
        value.setData(String.valueOf(System.currentTimeMillis()));
        System.out.printf("valueBound detail = %s%n", value.showDetail());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        LoginUser value = (LoginUser) event.getValue();
        System.out.printf("valueUnbound detail = %s%n", value.showDetail());
    }
}
