package com.example.sessionapi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String page;
        if ("caterpillar".equals(username) && "123456".equals(password)) {
            if (null != req.getSession(false)) {
                req.changeSessionId();
            }
            System.out.printf("%s : session id = %s%n", getClass().getName(), req.getSession().getId());
            req.getSession().setAttribute("login", username);
            page = "user";
        } else {
            page = "login.html";
        }
        resp.sendRedirect(page);
    }
}
