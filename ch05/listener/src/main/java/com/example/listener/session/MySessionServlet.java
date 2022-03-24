package com.example.listener.session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@WebServlet("/update-session")
public class MySessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String name = req.getParameter("name");
        String value = req.getParameter("value");
        Integer age = Optional.ofNullable(req.getParameter("age"))
                .map(Integer::valueOf)
                .orElse(null);
        if (null == value) {
            session.removeAttribute(name);
            session.removeAttribute("user");
        } else {
            session.setAttribute(name, value);
            session.setAttribute("user", new User(name, age));
        }
    }
}
