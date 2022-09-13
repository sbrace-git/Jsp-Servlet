package cc.openhome.gossip.listener;

import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.dao.MessageDao;
import cc.openhome.gossip.dao.impl.AccountDaoImpl;
import cc.openhome.gossip.dao.impl.MessageDaoImpl;
import cc.openhome.gossip.service.UserService;
import cc.openhome.gossip.template.JdbcTemplate;
import cc.openhome.gossip.template.impl.JdbcTemplateImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ServiceContainer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        DataSource dataSource;
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:/comp/env");
            dataSource = (DataSource) context.lookup("jdbc/gossip/h2");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl(dataSource);
        AccountDao accountDao = new AccountDaoImpl(jdbcTemplate);
        MessageDao messageDao = new MessageDaoImpl(jdbcTemplate);

        servletContext.setAttribute("userService", new UserService(accountDao, messageDao));
    }
}
