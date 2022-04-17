package com.example.onlineuser.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collections;

@WebListener
public class LoginSessionListener implements HttpSessionListener {

    private static int counter;

    public static int getCounter() {
        return counter;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("LoginSessionListener # sessionCreated");
        HttpSession session = se.getSession();
        Collections.list(session.getAttributeNames())
                .forEach(attributeName ->
                        System.out.printf("%s = %s%n",attributeName,session.getAttribute(attributeName)));
        counter++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("LoginSessionListener # sessionDestroyed");
        counter--;
    }
}
