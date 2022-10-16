package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/login",
        initParams = {
                @WebInitParam(name = "SUCCESS_PATH", value = "member"),
                @WebInitParam(name = "LOGIN_PATH", value = "/WEB-INF/jsp/index.jsp")
        }
)
public class LoginController extends HttpServlet {

    private final Logger logger = Logger.getLogger(LoginController.class.getName());

    private static String SUCCESS_PATH;
    private static String LOGIN_PATH;
    private UserService userService;

    @Override
    public void init() {
        SUCCESS_PATH = getInitParameter("SUCCESS_PATH");
        LOGIN_PATH = getInitParameter("LOGIN_PATH");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<String> optionalPasswd = userService.encryptedPassword(username, password);

        try {
            if (!optionalPasswd.isPresent()) {
                throw new RuntimeException("account error");
            }
            logger.log(Level.INFO, "username = {0}", username);
            logger.log(Level.INFO, "passwd = {0}", optionalPasswd.get());
            req.login(username, optionalPasswd.get());
            if (null != req.getSession(false)) {
                req.changeSessionId();
            }
            req.getSession().setAttribute("login", username);
            resp.sendRedirect(SUCCESS_PATH);
            logger.log(Level.INFO, "{0} login success", username);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "login error", e);
            List<Message> newMessageList = userService.newMessageList();
            req.setAttribute("newMessageList", newMessageList);
            ArrayList<String> errors = new ArrayList<>();
            errors.add("登录失败");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
        }
    }
}
