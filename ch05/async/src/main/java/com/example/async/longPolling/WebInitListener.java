package com.example.async.longPolling;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class WebInitListener implements ServletContextListener {

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
            asyncList.forEach(ctx -> {
                try {
                    ctx.getResponse().getWriter().println(num);
                    ctx.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            asyncList.clear();
        }
    }
}
