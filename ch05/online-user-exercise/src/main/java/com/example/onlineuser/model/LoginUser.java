package com.example.onlineuser.model;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.ZoneOffset;

public class LoginUser {
    private String name;
    private String userAgent;
    private HttpSession httpSession;

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
        return String.format("%s\t%s\t%s\t%s",
                name, sessionId, lastAccessedTimeStr, userAgent);
    }
}
