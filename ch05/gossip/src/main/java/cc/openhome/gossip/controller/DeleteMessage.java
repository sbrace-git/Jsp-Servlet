package cc.openhome.gossip.controller;

import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(urlPatterns = "/del_message",
        initParams = {
                @WebInitParam(name = "LOGIN_PATH", value = "index.html"),
                @WebInitParam(name = "MEMBER_PATH", value = "member")
        }
)
public class DeleteMessage extends HttpServlet {

    private String MEMBER_PATH;

    private UserService userService;

    @Override
    public void init() {
        MEMBER_PATH = getInitParameter("MEMBER_PATH");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("login");
        String millis = req.getParameter("millis");
        if (null != millis) {
            userService.deleteMessage(username, millis);
        }
        resp.sendRedirect(MEMBER_PATH);
    }
}
