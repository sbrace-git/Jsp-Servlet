package com.example.onlineuser.listener;

import com.example.onlineuser.model.LoginUser;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class LoginUserSessionAttributeListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getValue() instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) event.getValue();
            loginUser.setData(String.valueOf(System.currentTimeMillis()));
            System.out.printf("attributeAdded detail = %s%n", loginUser.showDetail());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getValue() instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) event.getValue();
            System.out.printf("attributeRemoved detail = %s%n", loginUser.showDetail());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if (event.getValue() instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) event.getValue();
            System.out.printf("attributeReplaced detail = %s%n", loginUser.showDetail());
        }
    }
}
