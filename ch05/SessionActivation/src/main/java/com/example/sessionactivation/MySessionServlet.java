package com.example.sessionactivation;

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
        if (null == name) {
            Person person = (Person) session.getAttribute("person");
            System.out.printf("MySessionServlet person = %s%n", person);
            Object test = session.getAttribute("test");
            System.out.println(test);
        } else {
            session.setAttribute("person", new Person(name));
            session.setAttribute("test","test");
        }
    }
}
