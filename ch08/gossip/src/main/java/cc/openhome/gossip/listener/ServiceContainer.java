package cc.openhome.gossip.listener;

import cc.openhome.gossip.dao.impl.AccountDaoImpl;
import cc.openhome.gossip.dao.impl.MessageDaoImpl;
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
        AccountDaoImpl accountDao = new AccountDaoImpl(USERS);
        MessageDaoImpl messageDao = new MessageDaoImpl(USERS);
        servletContext.setAttribute("userService", new UserService(accountDao, messageDao));
    }
}
