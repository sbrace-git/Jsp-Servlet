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
import java.util.List;

@WebServlet(urlPatterns = "/user/*",
        initParams = {
                @WebInitParam(name = "USER_PATH", value = "/WEB-INF/jsp/user.jsp")
        }
)
public class User extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.printf("pathInfo = 【%s】", pathInfo);
        String username = pathInfo.substring(1);
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<Message> messages = userService.messages(username);
        req.setAttribute("username", username);
        req.setAttribute("messages", messages);

        req.getRequestDispatcher(getInitParameter("USER_PATH")).forward(req, resp);
    }
}
