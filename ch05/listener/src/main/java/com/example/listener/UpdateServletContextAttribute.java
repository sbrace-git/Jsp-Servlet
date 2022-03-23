package com.example.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateServletContextAttribute")
public class UpdateServletContextAttribute extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String value = req.getParameter("value");
        ServletContext servletContext = getServletContext();
        if (null == value) {
            servletContext.removeAttribute(name);
        } else {
            servletContext.setAttribute(name, value);
        }
    }
}
