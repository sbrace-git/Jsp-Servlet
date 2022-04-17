package com.example.onlineuser.listener;

import com.example.onlineuser.model.LoginUser;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

@WebListener
public class LoginSessionListener implements HttpSessionListener {


    private static final Map<String, LoginUser> LOGIN_USER_MAP = new HashMap<>();

    public static int getCounter() {
        return LOGIN_USER_MAP.size();
    }

    public static Collection<LoginUser> getAllLoginUser() {
        return LOGIN_USER_MAP.values();
    }

    public static void login(LoginUser loginUser, String oldSessionId) {
        Optional.ofNullable(oldSessionId)
                .ifPresent(LOGIN_USER_MAP::remove);
        Optional.ofNullable(loginUser)
                .map(LoginUser::getHttpSession)
                .map(HttpSession::getId)
                .map(id -> LOGIN_USER_MAP.put(id, loginUser));
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // session 不一定通过 Login 创建, 所以 LOGIN_USER_MAP.put 不能写在本方法.
        // 写在了 login 方法
        System.out.println("LoginSessionListener # sessionCreated");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("LoginSessionListener # sessionDestroyed");
        LoginUser logout = LOGIN_USER_MAP.remove(se.getSession().getId());
        System.out.printf("loginUser = %s%n", Optional.ofNullable(logout)
                .map(LoginUser::showDetail).orElse(null));
    }
}
