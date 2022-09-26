package com.example.dispatcher.character.encoding;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/someInclude-character-encoding")
public class SomeInclude extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SomeInclude.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.log(Level.INFO, "characterEncoding = {0}", req.getCharacterEncoding());
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        logger.log(Level.INFO, "characterEncoding = {0}", req.getCharacterEncoding());

        PrintWriter writer = resp.getWriter();
        writer.println("Some do one...");

        RequestDispatcher dispatcher = req.getRequestDispatcher("other-character-encoding");
        dispatcher.include(req, resp);

        logger.log(Level.INFO, "characterEncoding = {0}", req.getCharacterEncoding());

        writer.println("Some do two...");
    }
}
