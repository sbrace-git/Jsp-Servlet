package com.example.dispatcher;

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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/someForward")
public class SomeForward extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SomeForward.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("\n parameters:");
        req.getParameterMap().forEach((k, v) ->
                logger.log(Level.INFO, "{0} = {1}", new Object[]{k, Arrays.toString(v)}));

        logger.info("\n headers:");
        Collections.list(req.getHeaderNames())
                .forEach(headerName ->
                        logger.log(Level.INFO,"{0} = {1}", new Object[]{headerName,req.getHeader(headerName)}));

        logger.log(Level.INFO, "\ncharacterEncoding = {0}\n", req.getCharacterEncoding());

        PrintWriter writer = resp.getWriter();
        writer.println("Some do one...");

        List<Book> books = Arrays.asList(
                new Book("《JSP & Servlet学习笔记（第3版）》", "林信良"),
                new Book("《Java学习笔记》", "林信良")
        );
        req.setAttribute("books", books);

        RequestDispatcher dispatcher = req.getRequestDispatcher("other?key=value");

        logger.info("before forward\n");

        dispatcher.forward(req, resp);

        logger.info("after forward");

        writer.println("Some do two...");
    }
}