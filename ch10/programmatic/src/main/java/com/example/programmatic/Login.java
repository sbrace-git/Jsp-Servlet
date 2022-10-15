package com.example.programmatic;

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
        String user = req.getParameter("user");
        String passwd = req.getParameter("passwd");
        try {
            req.login(user, passwd);
            resp.sendRedirect("user");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("login.html");
        }
    }
}
