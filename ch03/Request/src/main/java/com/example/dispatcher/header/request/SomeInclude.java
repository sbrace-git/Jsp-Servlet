package com.example.dispatcher.header.request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/someInclude-header-request")
public class SomeInclude extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SomeInclude.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collections.list(req.getHeaderNames())
                .forEach(headerName ->
                        logger.log(Level.INFO, "request header {0} = {1}", new Object[]{headerName, req.getHeader(headerName)}));

        PrintWriter writer = resp.getWriter();
        writer.println("Some do one...");

        RequestDispatcher dispatcher = req.getRequestDispatcher("other-header-request");
        dispatcher.include(req, resp);

        writer.println("Some do two...");
    }
}
