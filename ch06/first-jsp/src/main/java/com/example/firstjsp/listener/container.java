package com.example.firstjsp.listener;

import com.example.firstjsp.service.BookmarkService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class container implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("bookmarkService", new BookmarkService());
    }
}
