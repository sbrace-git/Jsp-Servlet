package com.example.async.serverSendEvent;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@WebListener
public class WebInitListener2 implements ServletContextListener {

    private Queue<AsyncContext> asyncContextQueue = new ConcurrentLinkedDeque<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("asyncQueue", asyncContextQueue);
        new Thread(() -> {
            while (true) {
                try {
                    int sleep = 5000;
                    System.out.printf("sleep = %d%n", sleep);
                    Thread.sleep(sleep);
                    response();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void response() {
        asyncContextQueue.forEach(asyncContext -> {
            try {
                PrintWriter writer = asyncContext.getResponse().getWriter();
                writer.printf("data : %s\n\n", Math.random());
                writer.flush();
                asyncContext.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
