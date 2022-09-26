package com.example.dispatcher.header.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/other-header-response")
public class Other extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Other.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Other", "Other");
        resp.getHeaderNames().forEach(header -> logger.log(Level.INFO, "response header {0} = {1}",
                new String[]{header, resp.getHeader(header)}));
        logger.log(Level.INFO, "");

        resp.getWriter().println("Other do one...");
    }
}
