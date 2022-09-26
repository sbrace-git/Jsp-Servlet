package com.example.dispatcher.param;

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

@WebServlet("/someInclude-param")
public class SomeInclude extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SomeInclude.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getParameterMap().forEach((k, v) ->
                logger.log(Level.INFO, "{0} = {1}", new Object[]{k, Arrays.toString(v)}));

        PrintWriter writer = resp.getWriter();
        writer.println("Some do one...");

        RequestDispatcher dispatcher = req.getRequestDispatcher("other-param?key=value");
        dispatcher.include(req, resp);

        writer.println("Some do two...");
    }
}
