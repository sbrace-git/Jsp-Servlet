package com.example.async.longPolling;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/asyncNumber", asyncSupported = true)
public class AsyncNumber extends HttpServlet {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private List<AsyncContext> asyncList;

    @Override
    public void init() {
        asyncList = (List<AsyncContext>) getServletContext().getAttribute("asyncList");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        synchronized (asyncList) {
            AsyncContext asyncContext = req.startAsync();
//            asyncContext.setTimeout(20000L);
            asyncContext.addListener(new AsyncListener() {
                @Override
                public void onComplete(AsyncEvent event) {
                    logger.log(Level.INFO, "AsyncEvent onComplete");
                }

                @Override
                public void onTimeout(AsyncEvent event) {
                    // TODO: 超时 从 asyncList 移除？
                    logger.log(Level.INFO, "AsyncEvent onTimeout");
                }

                @Override
                public void onError(AsyncEvent event) {
                    logger.log(Level.INFO, "AsyncEvent onError");
                }

                @Override
                public void onStartAsync(AsyncEvent event) {
                    logger.log(Level.INFO, "AsyncEvent onStartAsync");
                }
            });
            asyncList.add(asyncContext);
        }
    }
}
