package com.example.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
            processCookie(req, resp);
            page = "user";
        } else {
            page = "login.html";
        }
        resp.sendRedirect(page);
    }

    private void processCookie(HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = new Cookie("user","caterpillar");
        if ("true".equals(req.getParameter("auto"))) {
            cookie.setMaxAge(7 * 24 * 60 * 60);
        }
        resp.addCookie(cookie);
    }
}
