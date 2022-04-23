package cc.openhome.gossip.controller;

import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

//@WebServlet(urlPatterns = "/login",
//        initParams = {
//                @WebInitParam(name = "SUCCESS_PATH", value = "member"),
//                @WebInitParam(name = "LOGIN_PATH", value = "/WEB-INF/jsp/index.jsp")
//        }
//)
public class Login extends HttpServlet {

    private String SUCCESS_PATH;

    private String LOGIN_PATH;

    private UserService userService;

    @Override
    public void init() {
        SUCCESS_PATH = getInitParameter("SUCCESS_PATH");
        LOGIN_PATH = getInitParameter("LOGIN_PATH");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userService.login(username, password)) {
            if (null != req.getSession(false)) {
                req.changeSessionId();
            }
            req.getSession().setAttribute("login", username);
            resp.sendRedirect(SUCCESS_PATH);
        } else {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("用户名或密码错误");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
        }
    }
}
