package com.example.onlineuser.controller;

import com.example.onlineuser.listener.LoginUserListener;
import com.example.onlineuser.model.LoginUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class Login extends HttpServlet {

    private final static Map<String, String> users;

    static {
        users = new HashMap<>();
        users.put("caterpillar", "123456");
        users.put("momor", "98765");
        users.put("hamimi", "13579");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (users.containsKey(username) && users.get(username).equals(password)) {
            HttpSession session = req.getSession(false);
            String oldSessionId = null;
            if (null != session) {
                oldSessionId = session.getId();
                req.changeSessionId();
            } else {
                session = req.getSession();
            }
            LoginUser loginUser = new LoginUser();
            loginUser.setName(username);
            loginUser.setUserAgent(req.getHeader("user-agent"));
            loginUser.setHttpSession(session);
            LoginUserListener.login(loginUser, oldSessionId);
            session.setAttribute("loginUser", loginUser);
            resp.sendRedirect("welcome.view");
            return;
        }
        resp.sendRedirect("form.html");
    }
}
