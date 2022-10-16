package com.example.fileservice.linstener;

import com.example.fileservice.service.FileService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CreateBean implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FileService fileService = new FileService();
        sce.getServletContext().setAttribute("fileService", fileService);
    }
}
