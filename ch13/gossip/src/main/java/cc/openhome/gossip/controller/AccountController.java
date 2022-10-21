package cc.openhome.gossip.controller;

import cc.openhome.gossip.constant.Regex;
import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.EmailService;
import cc.openhome.gossip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${redirect.member.path}")
    private String REDIRECT_MEMBER_PATH;
    @Value("${login.view.path}")
    private String LOGIN_VIEW_PATH;
    @Value("${redirect.login.path}")
    private String REDIRECT_LOGIN_PATH;
    @Value("${forget.view.path}")
    private String FORGET_VIEW_PATH;
    @Value("${reset.password.view.path}")
    private String RESET_PASSWORD_VIEW_PATH;
    @Value("${reset.success.view.path}")
    private String RESET_SUCCESS_VIEW_PATH;
    @Value("${register.success.view.path}")
    private String REGISTER_SUCCESS_VIEW_PATH;
    @Value("${register.form.view.path}")
    private String REGISTER_FORM_VIEW_PATH;
    @Value("${verify.view.path}")
    private String VERIFY_VIEW_PATH;

    @PostMapping("/login")
    public String login(String username, String password, HttpServletRequest req) {

        Optional<String> optionalPasswd = userService.encryptedPassword(username, password);

        try {
            if (!optionalPasswd.isPresent()) {
                throw new RuntimeException("account error");
            }
            req.login(username, optionalPasswd.get());
            if (null != req.getSession(false)) {
                req.changeSessionId();
            }
            req.getSession().setAttribute("login", username);
            return REDIRECT_MEMBER_PATH;
        } catch (Exception e) {
            logger.log(Level.WARNING, "login error", e);
            List<Message> newMessageList = userService.newMessageList();
            req.setAttribute("newMessageList", newMessageList);
            ArrayList<String> errors = new ArrayList<>();
            errors.add("登录失败");
            req.setAttribute("errors", errors);
            return LOGIN_VIEW_PATH;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) throws ServletException {
        req.getSession().invalidate();
        req.logout();
        return REDIRECT_LOGIN_PATH;
    }

    @PostMapping("/forgot")
    public String forgot(HttpServletRequest req) {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        Optional<Account> accountByNameEmail = userService.getAccountByNameEmail(name, email);
        accountByNameEmail.ifPresent(emailService::passwordResetLink);
        req.setAttribute("email", email);
        return FORGET_VIEW_PATH;
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(HttpServletRequest req)
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
                return RESET_PASSWORD_VIEW_PATH;
            }
        }
        return REDIRECT_LOGIN_PATH;
    }

    @PostMapping("/reset-password")
    public String resetPassword(HttpServletRequest req) {
        String token = req.getParameter("token");
        String storedToken = (String) req.getSession().getAttribute("token");
        if (null == storedToken || !storedToken.equals(token)) {
            return REDIRECT_LOGIN_PATH;
        }

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");

        if (null == password || !Regex.passwdRegex.matcher(password).find() || !password.equals(password2)) {
            req.setAttribute("errors", Collections.singletonList("请确认密码符合格式并再度确认密码"));
            return RESET_PASSWORD_VIEW_PATH;
        }
        Optional<Account> accountByNameEmail = userService.getAccountByNameEmail(name, email);
        req.setAttribute("account", accountByNameEmail.get());
        userService.resetPassword(name, password);
        return RESET_SUCCESS_VIEW_PATH;
    }

    @PostMapping("/register")
    public String register(HttpServletRequest req) throws IOException {
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
        return path;
    }

    @GetMapping("/register")
    public String registerForm() {
        return REGISTER_FORM_VIEW_PATH;
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
