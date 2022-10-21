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
import java.util.Collections;
import java.util.List;

@WebServlet(urlPatterns = "/user/*", initParams = {@WebInitParam(name = "USER_PATH", value = "/WEB-INF/jsp/user.jsp")})
public class UserController extends HttpServlet {

    private static String USER_PATH;

    private UserService userService;

    @Override
    public void init() {
        USER_PATH = getInitParameter("USER_PATH");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String username = pathInfo.substring(1);
        req.setAttribute("username", username);
        if (userService.userExist(username)) {
            List<Message> messages = userService.messages(username);
            req.setAttribute("messages", messages);
        } else {
            req.setAttribute("errors", Collections.singletonList(String.format("%s, 还没有发表信息", username)));
        }
        req.getRequestDispatcher(USER_PATH).forward(req, resp);
    }
}
