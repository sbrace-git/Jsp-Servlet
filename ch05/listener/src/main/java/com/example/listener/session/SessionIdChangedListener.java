package com.example.listener.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;

@WebListener
public class SessionIdChangedListener implements HttpSessionIdListener {

    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        String id = event.getSession().getId();
        System.out.printf("oldSessionId = %s, newSessionId = %s%n", oldSessionId, id);
    }
}
