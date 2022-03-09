package com.example.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebServlet("/user")
public class User extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Cookie> userCookie = getUserCookie(req.getCookies());
        if (userCookie.isPresent()) {
            Cookie cookie = userCookie.get();
            req.setAttribute("user", cookie.getValue());
            req.getRequestDispatcher("/user.view").forward(req, resp);
        } else {
            resp.sendRedirect("login.html");
        }
    }

    private Optional<Cookie> getUserCookie(Cookie[] cookies) {
        return Optional.ofNullable(cookies)
                .map(Arrays::stream)
                .flatMap(cookieStream ->
                        cookieStream.filter(item -> "user".equals(item.getName())).findFirst()
                );
    }
}
