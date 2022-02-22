package com.example.login;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        System.out.printf("name = %s%n", name);
        String pwd = req.getParameter("pwd");
        System.out.printf("pwd = %s%n", pwd);
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        if ("caterpillar".equals(name) && "123456".equals(pwd)) {
            writer.append("<!DOCTYPE html>")
                    .append("<html>")
                    .append("<head>")
                    .append("<title>")
                    .append("login success")
                    .append("</title>")
                    .append("</head>")
                    .append("<body>")
                    .append("login success")
                    .append("</body>")
                    .append("</html>");
        } else {
            writer.append("<!DOCTYPE html>")
                    .append("<html>")
                    .append("<head>")
                    .append("<title>")
                    .append("login fail")
                    .append("</title>")
                    .append("</head>")
                    .append("<body>")
                    .append("login fail<br/>")
                    .append("<a href='login.html'>login page</a>")
                    .append("</body>")
                    .append("</html>");
        }
    }
}
