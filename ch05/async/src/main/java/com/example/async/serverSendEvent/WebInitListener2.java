package com.example.async.serverSendEvent;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class WebInitListener2 implements ServletContextListener {

    private final Logger logger = Logger.getLogger(WebInitListener2.class.getName());

    private Queue<AsyncContext> asyncContextQueue = new ConcurrentLinkedDeque<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("asyncQueue", asyncContextQueue);
        new Thread(() -> {
            while (true) {
                try {
                    int sleep = 3000;
//                    System.out.println("sleep = " + sleep);
                    Thread.sleep(sleep);
                    response();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void response() {
        logger.log(Level.INFO, "asyncContextQueue.size = {0}", asyncContextQueue.size());
        asyncContextQueue.forEach(asyncContext -> {
            try {
                logger.log(Level.INFO, "asyncContext = {0}", asyncContext);
                PrintWriter writer = asyncContext.getResponse().getWriter();
                writer.printf("data:%s\n\n", Math.random());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
