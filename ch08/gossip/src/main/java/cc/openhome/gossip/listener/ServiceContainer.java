package cc.openhome.gossip.listener;

import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServiceContainer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        final String USERS = servletContext.getInitParameter("USERS");
        servletContext.setAttribute("userService", new UserService(USERS));
    }
}
