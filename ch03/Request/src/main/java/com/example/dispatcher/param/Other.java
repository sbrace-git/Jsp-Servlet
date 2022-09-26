package com.example.dispatcher.param;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/other-param")
public class Other extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Other.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getParameterMap().forEach((k, v) ->
                logger.log(Level.INFO, "{0} = {1}", new Object[]{k, Arrays.toString(v)}));

        resp.getWriter().println("Other do one...");
    }
}
