package com.example.onlineuser.listener;

import com.example.onlineuser.model.LoginUser;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;
import java.util.stream.Collectors;

@WebListener
public class LoginSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("LoginSessionListener # sessionCreated");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("LoginSessionListener # sessionDestroyed");
        LoginUser logout = LoginUserListener.logout(se.getSession().getId());
        System.out.printf("loginUser = %s%n", Optional.ofNullable(logout)
                .map(LoginUser::showDetail).orElse(null));
    }
}
