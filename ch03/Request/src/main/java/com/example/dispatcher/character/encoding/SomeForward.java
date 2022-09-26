package com.example.dispatcher.character.encoding;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/someForward-character-encoding")
public class SomeForward extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SomeForward.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.log(Level.INFO, "characterEncoding = {0}", req.getCharacterEncoding());
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        logger.log(Level.INFO, "characterEncoding = {0}", req.getCharacterEncoding());

        RequestDispatcher dispatcher = req.getRequestDispatcher("other-character-encoding");
        dispatcher.forward(req, resp);

        logger.log(Level.INFO, "characterEncoding = {0}", req.getCharacterEncoding());
    }
}