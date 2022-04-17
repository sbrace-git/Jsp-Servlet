package com.example.onlineuser.listener;

import com.example.onlineuser.model.LoginUser;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LoginUserListener implements ServletContextListener {

    private static final Map<String, LoginUser> LOGIN_USER_MAP = new HashMap<>();

    public static int getCounter() {
        return LOGIN_USER_MAP.size();
    }

    public static List<String> getAllLoginUser() {
        return LOGIN_USER_MAP.values().stream().map(LoginUser::showDetail).collect(Collectors.toList());
    }

    public static LoginUser login(LoginUser loginUser, String oldSessionId) {
        Optional.ofNullable(oldSessionId)
                .ifPresent(LOGIN_USER_MAP::remove);
        return Optional.ofNullable(loginUser)
                .map(LoginUser::getHttpSession)
                .map(HttpSession::getId)
                .map(id -> LOGIN_USER_MAP.put(id, loginUser))
                .orElse(null);
    }

    public static LoginUser logout(String sessionId) {
        return LOGIN_USER_MAP.remove(sessionId);
    }
}
