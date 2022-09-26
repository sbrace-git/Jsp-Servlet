package com.example.dispatcher.url;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/someInclude-url")
public class SomeInclude extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SomeInclude.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.log(Level.INFO, "contextPath = {0}", req.getContextPath());
        logger.log(Level.INFO, "requestURL = {0}", req.getRequestURL().toString());
        logger.log(Level.INFO, "requestURI = {0}", req.getRequestURI());
        logger.log(Level.INFO, "servletPath = {0}", req.getServletPath());
        logger.log(Level.INFO, "pathInfo = {0}", req.getPathInfo());
        logger.log(Level.INFO, "queryString = {0}", req.getQueryString());
        logger.log(Level.INFO, "mapping = {0}\n", req.getHttpServletMapping().getMappingMatch().name());

        PrintWriter writer = resp.getWriter();
        writer.println("Some do one...");

        RequestDispatcher dispatcher = req.getRequestDispatcher("other-url?key=value");
        dispatcher.include(req, resp);

        writer.println("Some do two...");
    }
}
