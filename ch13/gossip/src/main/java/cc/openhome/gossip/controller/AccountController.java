package cc.openhome.gossip.controller;

import cc.openhome.gossip.constant.Regex;
import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.EmailService;
import cc.openhome.gossip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AccountController {
    private final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private static final String MEMBER_PATH = "member";
    private static final String LOGIN_VIEW_PATH = "/WEB-INF/jsp/index.jsp";
    private static final String LOGIN_PATH = "/gossip";
    private static final String FORGET_VIEW_PATH = "/WEB-INF/jsp/forgot.jsp";
    private static final String RESET_PASSWORD_VIEW_PATH = "/WEB-INF/jsp/reset_password.jsp";
    private static final String RESET_SUCCESS_VIEW_PATH = "/WEB-INF/jsp/reset_success.jsp";
    private static final String REGISTER_SUCCESS_VIEW_PATH = "/WEB-INF/jsp/register_success.jsp";
    private static final String REGISTER_FORM_VIEW_PATH = "/WEB-INF/jsp/register.jsp";
    private static final String VERIFY_VIEW_PATH = "/WEB-INF/jsp/verify.jsp";

    @PostMapping("/login")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    @PostMapping("/forgot")
    public void forgot(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        Optional<Account> accountByNameEmail = userService.getAccountByNameEmail(name, email);
        accountByNameEmail.ifPresent(emailService::passwordResetLink);
        req.setAttribute("email", email);
        req.getRequestDispatcher(FORGET_VIEW_PATH).forward(req, resp);
    }

    @GetMapping("/reset-password")
    public void resetPasswordForm(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String name = req.getParameter("name");
        String token = req.getParameter("token");
        String email = req.getParameter("email");
        Optional<Account> accountByEmail = userService.getAccountByNameEmail(name, email);
        if (accountByEmail.isPresent()) {
            Account account = accountByEmail.get();
            if (account.getPassword().equals(token)) {
                req.setAttribute("account", account);
                req.getSession().setAttribute("token", token);
                req.getRequestDispatcher(RESET_PASSWORD_VIEW_PATH).forward(req, resp);
                return;
            }
        }
        resp.sendRedirect(LOGIN_PATH);
    }

    @PostMapping("/reset-password")
    public void resetPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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

        if (null == password || !Regex.passwdRegex.matcher(password).find() || !password.equals(password2)) {
            req.setAttribute("errors", Collections.singletonList("请确认密码符合格式并再度确认密码"));
            req.getRequestDispatcher(RESET_PASSWORD_VIEW_PATH).forward(req, resp);
            return;
        }
        Optional<Account> accountByNameEmail = userService.getAccountByNameEmail(name, email);
        req.setAttribute("account", accountByNameEmail.get());
        userService.resetPassword(name, password);
        req.getRequestDispatcher(RESET_SUCCESS_VIEW_PATH).forward(req, resp);

    }

    @PostMapping("/register")
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
            path = REGISTER_SUCCESS_VIEW_PATH;
            Optional<Account> account = userService.tryCreateUser(email, username, password);
            if (account.isPresent()) {
                emailService.validationLink(account.get());
            } else {
                emailService.failedRegistration(username, email);
            }
        } else {
            path = REGISTER_FORM_VIEW_PATH;
            req.setAttribute("errors", errors);
        }
        req.getRequestDispatcher(path).forward(req, resp);

    }

    @GetMapping("/register")
    public void registerForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTER_FORM_VIEW_PATH).forward(req, resp);
    }

    private boolean validateEmail(String email) {
        return email != null && Regex.emailRegex.matcher(email).find();
    }

    private boolean validateUsername(String username) {
        return username != null && Regex.usernameRegex.matcher(username).find();
    }

    private boolean validatePassword(String password, String password2) {
        return password != null && Regex.passwdRegex.matcher(password).find() && password.equals(password2);
    }

    @GetMapping("/verify")
    public void verify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String token = req.getParameter("token");
        Optional<Account> account = userService.verify(email, token);
        req.setAttribute("account", account);
        req.getRequestDispatcher(VERIFY_VIEW_PATH).forward(req, resp);
    }
}
