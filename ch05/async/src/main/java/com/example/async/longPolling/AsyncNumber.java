package com.example.async.longPolling;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/asyncNumber", asyncSupported = true)
public class AsyncNumber extends HttpServlet {

    private List<AsyncContext> asyncList;

    @Override
    public void init() {
        asyncList = (List<AsyncContext>) getServletContext().getAttribute("asyncList");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        synchronized (asyncList) {
            AsyncContext asyncContext = req.startAsync();
            asyncContext.addListener(new AsyncListener() {
                @Override
                public void onComplete(AsyncEvent event) {
                    System.out.println("AsyncEvent onComplete");
                }

                @Override
                public void onTimeout(AsyncEvent event) {
                    System.out.println("AsyncEvent onTimeout");
                }

                @Override
                public void onError(AsyncEvent event) {
                    System.out.println("AsyncEvent onError");
                }

                @Override
                public void onStartAsync(AsyncEvent event) {
                    System.out.println("AsyncEvent onStartAsync");
                }
            });
            asyncList.add(asyncContext);
        }
    }
}
