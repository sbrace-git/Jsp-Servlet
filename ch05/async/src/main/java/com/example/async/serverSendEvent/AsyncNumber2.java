package com.example.async.serverSendEvent;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/asyncNumber2", asyncSupported = true)
public class AsyncNumber2 extends HttpServlet {

    private final Logger logger = Logger.getLogger(AsyncNumber2.class.getName());

    private Queue<AsyncContext> asyncContextQueue;

    @Override
    public void init() {
        asyncContextQueue = (Queue<AsyncContext>) getServletContext().getAttribute("asyncQueue");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.log(Level.INFO, Thread.currentThread().getId() + " AsyncNumber2 doGet");
        resp.setContentType("text/event-stream");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(30 * 1000);

        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) {
                logger.log(Level.INFO, Thread.currentThread().getId() + " AsyncNumber2 onComplete");
                asyncContextQueue.remove(event.getAsyncContext());
            }

            @Override
            public void onTimeout(AsyncEvent event) {
                logger.log(Level.INFO, Thread.currentThread().getId() + " AsyncNumber2 onTimeout");
                asyncContextQueue.remove(event.getAsyncContext());
            }

            @Override
            public void onError(AsyncEvent event) {
                Throwable throwable = event.getThrowable();
                throwable.printStackTrace();
                logger.log(Level.INFO, Thread.currentThread().getId() + " AsyncNumber2 onError");
                asyncContextQueue.remove(event.getAsyncContext());
            }

            @Override
            public void onStartAsync(AsyncEvent event) {
                logger.log(Level.INFO, Thread.currentThread().getId() + " AsyncNumber2 onStartAsync");
            }
        });
        asyncContextQueue.add(asyncContext);
    }
}
