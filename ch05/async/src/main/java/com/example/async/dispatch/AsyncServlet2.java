package com.example.async.dispatch;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/async2", asyncSupported = true)
public class AsyncServlet2 extends HttpServlet {

    Logger logger = Logger.getLogger(AsyncServlet2.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.log(Level.INFO, "doGet");

        Collections.list(req.getAttributeNames()).forEach(attributeName -> logger.log(Level.INFO, "{0} = {1}",
                new Object[]{attributeName, req.getAttribute(attributeName)}));

        AsyncContext asyncContext = req.startAsync();
        CompletableFuture.runAsync(() -> {
            try {
                asyncContext.getResponse().getWriter().println("AsyncServlet2 response");
                asyncContext.complete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
