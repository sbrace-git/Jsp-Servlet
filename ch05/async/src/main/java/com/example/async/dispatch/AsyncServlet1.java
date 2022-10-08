package com.example.async.dispatch;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/async1", asyncSupported = true)
public class AsyncServlet1 extends HttpServlet {

    Logger logger = Logger.getLogger(AsyncServlet1.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.log(Level.INFO, " doGet");
        logger.log(Level.INFO, " RequestURI = {0}", req.getRequestURI());

        AsyncContext asyncContext = req.startAsync();
        String dispatch = req.getParameter("dispatch");
        asyncContext.dispatch(dispatch);
    }
}
