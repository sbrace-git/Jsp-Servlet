package com.example.loginpassword;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        String passwd = (String) session.getAttribute("passwd");
        session.invalidate();
        if (null == passwd || !passwd.equals(password)) {
            resp.sendRedirect("login.html");
            return;
        }
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("密碼正確");
    }
}
