package com.example.async.longPolling;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class WebInitListener implements ServletContextListener {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private final List<AsyncContext> asyncList = new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("asyncList", asyncList);
        new Thread(() -> {
            while (true) {
                try {
//                    int sleep = (int) (Math.random() * 5000);
                    int sleep = 5000;
//                    System.out.printf("sleep = %d%n", sleep);
                    Thread.sleep(sleep);
                    response(Math.random());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void response(double num) {
        synchronized (asyncList) {
            logger.log(Level.INFO, "asyncList size = {0}", asyncList.size());
            asyncList.forEach(ctx -> {
                try {
                    // 超时 getRequest 报错 java.lang.IllegalStateException: AsyncContext关联的请求已经完成处理
                    ServletRequest request = ctx.getRequest();
                    String timestamp = request.getParameter("timestamp");
                    logger.log(Level.INFO, "timestamp = {0}", timestamp);

                    String key = request.getParameter("key");
                    logger.log(Level.INFO, "key = {0}", key);

                    String result = String.format("num = %s, timestamp = %s, key = %s", num, timestamp, key);
                    ctx.getResponse().getWriter().println(result);
                    ctx.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            asyncList.clear();
        }
    }
}
