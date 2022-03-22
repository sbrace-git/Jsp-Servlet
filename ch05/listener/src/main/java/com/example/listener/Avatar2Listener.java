package com.example.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebListener
public class Avatar2Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String avatar = servletContext.getInitParameter("AVATAR");
        Path path = Paths.get(avatar);
        System.out.printf("Avatar2Listener # contextInitialized path = %s%n", path);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("Avatar2", new Avatar2(path));
        servlet.addMapping("/avatar2");
    }
}
