package com.example.async.dispatch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/synchronousServlet")
public class SynchronousServlet extends HttpServlet {
    Logger logger = Logger.getLogger(SynchronousServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.log(Level.INFO, "doGet");

        Collections.list(req.getAttributeNames()).forEach(attributeName -> logger.log(Level.INFO, "{0} = {1}",
                new Object[]{attributeName, req.getAttribute(attributeName)}));

        resp.getWriter().println("synchronousServlet response");
    }
}
