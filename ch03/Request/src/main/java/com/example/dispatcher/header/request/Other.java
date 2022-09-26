package com.example.dispatcher.header.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/other-header-request")
public class Other extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Other.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Collections.list(req.getHeaderNames())
                .forEach(headerName ->
                        logger.log(Level.INFO, "request header {0} = {1}", new Object[]{headerName, req.getHeader(headerName)}));
        resp.getWriter().println("Other do one...");
    }
}
