package cc.openhome.gossip.controller;

import cc.openhome.gossip.constant.Regex;
import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.EmailService;
import cc.openhome.gossip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String login(String username, String password, HttpServletRequest req, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        Optional<String> optionalPasswd = userService.encryptedPassword(username, password);
        try {
            if (!optionalPasswd.isPresent()) {
                throw new RuntimeException("account error");
            }
            req.login(username, optionalPasswd.get());
            httpSession.setAttribute("login", username);
            return REDIRECT_MEMBER_PATH;
        } catch (Exception e) {
            logger.log(Level.WARNING, "login error", e);
            List<Message> newMessageList = userService.newMessageList();
            redirectAttributes.addFlashAttribute("newMessageList", newMessageList);
            List<String> errors = Collections.singletonList("登录失败");
            redirectAttributes.addFlashAttribute("errors", errors);
            return REDIRECT_LOGIN_PATH;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) throws ServletException {
        req.logout();
        return REDIRECT_LOGIN_PATH;
    }

    @PostMapping("/forgot")
    public String forgot(String name, String email, Model model) {
        Optional<Account> accountByNameEmail = userService.getAccountByNameEmail(name, email);
        accountByNameEmail.ifPresent(emailService::passwordResetLink);
        model.addAttribute("email", email);
        return FORGET_VIEW_PATH;
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(String name, String email, String token, HttpSession httpSession, Model model) {
        Optional<Account> accountByEmail = userService.getAccountByNameEmail(name, email);
        if (accountByEmail.isPresent()) {
            Account account = accountByEmail.get();
            if (account.getPassword().equals(token)) {
                model.addAttribute("account", account);
                httpSession.setAttribute("token", token);
                return RESET_PASSWORD_VIEW_PATH;
            }
        }
        return REDIRECT_LOGIN_PATH;
    }

    @PostMapping("/reset-password")
    public String resetPassword(String token, @SessionAttribute("token") String storedToken, String name,
                                String email, String password, String password2, Model model) {
        if (null == storedToken || !storedToken.equals(token)) {
            return REDIRECT_LOGIN_PATH;
        }

        if (null == password || !Regex.passwdRegex.matcher(password).find() || !password.equals(password2)) {
            model.addAttribute("errors", Collections.singletonList("请确认密码符合格式并再度确认密码"));
            return RESET_PASSWORD_VIEW_PATH;
        }
        Optional<Account> accountByNameEmail = userService.getAccountByNameEmail(name, email);
        model.addAttribute("account", accountByNameEmail.get());
        userService.resetPassword(name, password);
        return RESET_SUCCESS_VIEW_PATH;
    }

    @PostMapping("/register")
    public String register(String email, String username, String password, String password2, Model model)
            throws IOException {

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
            model.addAttribute("errors", errors);
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
    public String verify(String email, String token, Model model) {
        Optional<Account> account = userService.verify(email, token);
        model.addAttribute("account", account);
        return VERIFY_VIEW_PATH;
    }
}
