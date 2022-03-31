package com.example.async.longPolling;

import com.example.async.AsyncServlet;

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

        Object asyncContextPath = req.getAttribute(AsyncContext.ASYNC_CONTEXT_PATH);
        System.out.printf("asyncContextPath = %s%n", asyncContextPath);
        Object asyncMapping = req.getAttribute(AsyncContext.ASYNC_MAPPING);
        System.out.printf("asyncMapping = %s%n", asyncMapping);
        Object asyncPathInfo = req.getAttribute(AsyncContext.ASYNC_PATH_INFO);
        System.out.printf("asyncPathInfo = %s%n", asyncPathInfo);
        Object asyncRequestUri = req.getAttribute(AsyncContext.ASYNC_REQUEST_URI);
        System.out.printf("asyncRequestUri = %s%n", asyncRequestUri);
        Object asyncQueryString = req.getAttribute(AsyncContext.ASYNC_QUERY_STRING);
        System.out.printf("asyncQueryString = %s%n", asyncQueryString);
        Object asyncServletPath = req.getAttribute(AsyncContext.ASYNC_SERVLET_PATH);
        System.out.printf("asyncServletPath = %s%n", asyncServletPath);

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
