package com.example.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/avatar")
public class Avatar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        ServletContext servletContext = getServletContext();
        String avatarInit = servletContext.getInitParameter("AVATAR");
        System.out.printf("avatarInit = %s%n", avatarInit);
        String avatarAttribute = (String) servletContext.getAttribute("AVATAR");
        System.out.printf("avatarAttribute = %s%n", avatarAttribute);
        resp.getWriter().println(avatarAttribute);
    }
}
