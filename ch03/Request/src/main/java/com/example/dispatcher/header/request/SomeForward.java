package com.example.dispatcher.header.request;

import com.example.dispatcher.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/someForward-header-request")
public class SomeForward extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SomeForward.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collections.list(req.getHeaderNames())
                .forEach(headerName ->
                        logger.log(Level.INFO,"{0} = {1}", new Object[]{headerName,req.getHeader(headerName)}));

        RequestDispatcher dispatcher = req.getRequestDispatcher("other-header-request");

        dispatcher.forward(req, resp);

    }
}