package cc.openhome.gossip.controller;

import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;


@WebServlet(urlPatterns = "/register",
        initParams = {
                @WebInitParam(name = "SUCCESS_PATH", value = "/WEB-INF/jsp/register_success.jsp"),
                @WebInitParam(name = "FORM_PATH", value = "/WEB-INF/jsp/register.jsp")
        }
)
public class Register extends HttpServlet {
    private final static Pattern emailRegex = Pattern.compile(
            "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$");

    private final static Pattern passwdRegex = Pattern.compile("^\\w{8,16}$");

    private final static Pattern usernameRegex = Pattern.compile("^\\w{1,16}$");

    private String SUCCESS_PATH;
    private String FORM_PATH;
    private UserService userService;

    @Override
    public void init() {
        SUCCESS_PATH = getInitParameter("SUCCESS_PATH");
        FORM_PATH = getInitParameter("FORM_PATH");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(FORM_PATH).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");

        List<String> errors = new ArrayList<>();
        if (!validateEmail(email)) {
            errors.add("未填写邮件或邮件格式不正确");
        }
        if (!validateUsername(username)) {
            errors.add("未填写用户名或格式不正确");
        }
        if (!validatePassword(password, password2)) {
            errors.add("请确认密码符合格式并再次确认密码");
        }

        String path;
        if (errors.isEmpty()) {
            path = SUCCESS_PATH;
            userService.tryCreateUser(email,username,password);
        } else {
            path = FORM_PATH;
            req.setAttribute("errors", errors);
        }
        req.getRequestDispatcher(path).forward(req, resp);
    }

    private boolean validateEmail(String email) {
        return email != null && emailRegex.matcher(email).find();
    }

    private boolean validateUsername(String username) {
        return username != null && usernameRegex.matcher(username).find();
    }

    private boolean validatePassword(String password, String password2) {
        return password != null &&
                passwdRegex.matcher(password).find() &&
                password.equals(password2);
    }
}
