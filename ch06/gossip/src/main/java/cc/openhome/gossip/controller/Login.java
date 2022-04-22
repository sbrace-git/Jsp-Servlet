package cc.openhome.gossip.controller;

import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(urlPatterns = "/login",
        initParams = {
                @WebInitParam(name = "SUCCESS_PATH", value = "member"),
                @WebInitParam(name = "ERROR_PATH", value = "index.html")
        }
)
public class Login extends HttpServlet {

    private String SUCCESS_PATH;

    private String ERROR_PATH;

    private UserService userService;

    @Override
    public void init() {
        SUCCESS_PATH = getInitParameter("SUCCESS_PATH");
        ERROR_PATH = getInitParameter("ERROR_PATH");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String page;

        if (userService.login(username, password)) {
            if (null != req.getSession(false)) {
                req.changeSessionId();
            }
            req.getSession().setAttribute("login", username);
            page = SUCCESS_PATH;
        } else {
            page = ERROR_PATH;
        }
        resp.sendRedirect(page);
    }
}
