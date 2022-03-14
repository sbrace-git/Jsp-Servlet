package com.example.sessionapi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/user")
public class User extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.printf("%s : session id = %s%n", getClass().getName(), req.getSession().getId());
        System.out.printf("session.getMaxInactiveInterval() = %d%n", session.getMaxInactiveInterval());
        session.setMaxInactiveInterval(2500);
        System.out.printf("session.getMaxInactiveInterval() = %d%n", session.getMaxInactiveInterval());
        Optional<Object> token = Optional.ofNullable(session.getAttribute("login"));
        if (token.isPresent()) {
            req.getRequestDispatcher("user.view")
                    .forward(req, resp);
        } else {
            resp.sendRedirect("login.html");
        }
    }
}
