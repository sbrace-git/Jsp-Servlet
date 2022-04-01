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

@WebServlet(urlPatterns = "/asyncNumber2", asyncSupported = true)
public class AsyncNumber2 extends HttpServlet {
    private Queue<AsyncContext> asyncContextQueue;

    @Override
    public void init() throws ServletException {
        asyncContextQueue = (Queue<AsyncContext>) getServletContext().getAttribute("asyncQueue");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AsyncNumber2 doGet");
        resp.setContentType("text/event-stream");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(30 * 1000);

        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                asyncContextQueue.remove(event.getAsyncContext());
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                asyncContextQueue.remove(event.getAsyncContext());
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                asyncContextQueue.remove(event.getAsyncContext());
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("onStartAsync");
            }
        });
        asyncContextQueue.add(asyncContext);
    }
}
