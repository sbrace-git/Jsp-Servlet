package com.example.dispatcher.character.encoding;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/other-character-encoding")
public class Other extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Other.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.log(Level.INFO, "characterEncoding = {0}", req.getCharacterEncoding());

        req.setCharacterEncoding(StandardCharsets.ISO_8859_1.name());

        logger.log(Level.INFO, "characterEncoding = {0}", req.getCharacterEncoding());

        resp.getWriter().println("Other do one...");
    }
}
