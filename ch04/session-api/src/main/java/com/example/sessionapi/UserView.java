package com.example.sessionapi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user.view")
public class UserView extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.printf("%s : session id = %s%n", getClass().getName(), session.getId());
        System.out.printf("session.getMaxInactiveInterval() = %d%n", session.getMaxInactiveInterval());
        session.setMaxInactiveInterval(3500);
        System.out.printf("session.getMaxInactiveInterval() = %d%n", session.getMaxInactiveInterval());
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'/>");
        writer.println("</head>");
        writer.println("<body>");
        writer.printf("<h1>已登录%s</h1><br/>", req.getSession().getAttribute("login"));
        writer.println("<a href='logout'>注销</a>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
