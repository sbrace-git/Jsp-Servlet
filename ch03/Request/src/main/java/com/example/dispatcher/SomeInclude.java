package com.example.dispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/someInclude")
public class SomeInclude extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SomeInclude.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("\n parameters:");
        req.getParameterMap().forEach((k, v) ->
                logger.log(Level.INFO, "{0} = {1}", new Object[]{k, Arrays.toString(v)}));

        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        logger.log(Level.INFO, "\ncharacterEncoding = {0}\n", req.getCharacterEncoding());

        logger.info("\n headers:");
        Collections.list(req.getHeaderNames())
                .forEach(headerName ->
                        logger.log(Level.INFO, "{0} = {1}", new Object[]{headerName, req.getHeader(headerName)}));

        resp.setHeader("SomeInclude","SomeInclude");
        logger.log(Level.INFO, "\nresponse header SomeInclude = {0}\n", resp.getHeader("SomeInclude"));

        PrintWriter writer = resp.getWriter();
        writer.println("Some do one...");

        RequestDispatcher dispatcher = req.getRequestDispatcher("other?key=value");
        dispatcher.include(req, resp);

        logger.log(Level.INFO, "\ncharacterEncoding = {0}\n", req.getCharacterEncoding());

        logger.log(Level.INFO, "\nresponse header SomeInclude = {0}\n", resp.getHeader("SomeInclude"));
        logger.log(Level.INFO, "\nresponse header Other = {0}\n", resp.getHeader("Other"));
        writer.println("Some do two...");
    }
}
