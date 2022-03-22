package com.example.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextParameterListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String avatar = servletContext.getInitParameter("AVATAR");
        System.out.printf("AVATAR = %s%n", avatar);
        servletContext.setAttribute("AVATAR", avatar);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ContextParameterListener # contextDestroyed");
    }
}
