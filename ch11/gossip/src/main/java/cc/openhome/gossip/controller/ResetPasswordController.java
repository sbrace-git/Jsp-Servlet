package cc.openhome.gossip.controller;

import cc.openhome.gossip.constant.Regex;
import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@WebServlet(urlPatterns = "/reset-password",
        initParams = {
                @WebInitParam(name = "RESET_PASSWORD_PATH", value = "/WEB-INF/jsp/reset_password.jsp"),
                @WebInitParam(name = "RESET_SUCCESS_PATH", value = "/WEB-INF/jsp/reset_success.jsp")
        }

)
public class ResetPasswordController extends HttpServlet {

    private UserService userService;

    private String RESET_PASSWORD_PATH;
    private String RESET_SUCCESS_PATH;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        RESET_PASSWORD_PATH = getInitParameter("RESET_PASSWORD_PATH");
        RESET_SUCCESS_PATH = getInitParameter("RESET_SUCCESS_PATH");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String token = req.getParameter("token");
        String email = req.getParameter("email");

        Optional<Account> accountByEmail = userService.getAccountByNameEmail(name, email);
        if (accountByEmail.isPresent()) {
            Account account = accountByEmail.get();
            if (account.getPassword().equals(token)) {
                req.setAttribute("account", account);
                req.getSession().setAttribute("token", token);
                req.getRequestDispatcher(RESET_PASSWORD_PATH).forward(req, resp);
                return;
            }
        }
        resp.sendRedirect("/gossip");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String storedToken = (String) req.getSession().getAttribute("token");
        if (null == storedToken || !storedToken.equals(token)) {
            resp.sendRedirect("/gossip");
            return;
        }

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");

        if (null == password
                || !Regex.passwdRegex.matcher(password).find()
                || !password.equals(password2)) {
            req.setAttribute("errors", Collections.singletonList("请确认密码符合格式并再度确认密码"));
            req.getRequestDispatcher(RESET_PASSWORD_PATH).forward(req, resp);
            return;
        }

        Optional<Account> accountByNameEmail = userService.getAccountByNameEmail(name, email);
        req.setAttribute("account", accountByNameEmail.get());
        userService.resetPassword(name, password);
        req.getRequestDispatcher(RESET_SUCCESS_PATH).forward(req, resp);
    }
}
