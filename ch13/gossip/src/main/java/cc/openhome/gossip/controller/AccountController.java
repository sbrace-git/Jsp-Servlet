package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AccountController {
    private final Logger logger = Logger.getLogger(AccountController.class.getName());

    private static final String MEMBER_PATH = "member";
    private static final String LOGIN_VIEW_PATH = "/WEB-INF/jsp/index.jsp";
    private static final String LOGIN_PATH = "/gossip";

    @PostMapping("/login")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
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
            resp.sendRedirect(MEMBER_PATH);
            logger.log(Level.INFO, "{0} login success", username);
        } catch (Exception e) {
            logger.log(Level.WARNING, "login error", e);
            List<Message> newMessageList = userService.newMessageList();
            req.setAttribute("newMessageList", newMessageList);
            ArrayList<String> errors = new ArrayList<>();
            errors.add("登录失败");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(LOGIN_VIEW_PATH).forward(req, resp);
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        req.logout();
        resp.sendRedirect(LOGIN_PATH);
    }
}
