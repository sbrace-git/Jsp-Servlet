package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.EmailService;
import cc.openhome.gossip.service.UserService;
import cc.openhome.gossip.validator.RegisterForm;
import cc.openhome.gossip.validator.ResetPasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    // TODO: 2022/10/24 login spring-security
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
    public String resetPassword(@Valid ResetPasswordForm resetPasswordForm,
                                BindingResult bindingResult,
                                @SessionAttribute("token") String storedToken,
                                Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            model.addAttribute("errors", errors);
            return RESET_PASSWORD_VIEW_PATH;
        }

        String token = resetPasswordForm.getToken();
        if (null == storedToken || !storedToken.equals(token)) {
            return REDIRECT_LOGIN_PATH;
        }
        String name = resetPasswordForm.getName();
        String email = resetPasswordForm.getEmail();
        String password = resetPasswordForm.getPassword();
        Optional<Account> accountByNameEmail = userService.getAccountByNameEmail(name, email);
        if (!accountByNameEmail.isPresent()) {
            return REDIRECT_LOGIN_PATH;
        }
        model.addAttribute("account", accountByNameEmail.get());
        userService.resetPassword(name, password);
        return RESET_SUCCESS_VIEW_PATH;
    }

    @PostMapping("/register")
    public String register(@Valid RegisterForm registerForm, BindingResult bindingResult, Model model)
            throws IOException {
        String email = registerForm.getEmail();
        String username = registerForm.getUsername();
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errors);
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return REGISTER_FORM_VIEW_PATH;
        }
        String password = registerForm.getPassword();

        Optional<Account> account = userService.tryCreateUser(email, username, password);
        if (account.isPresent()) {
            emailService.validationLink(account.get());
        } else {
            emailService.failedRegistration(username, email);
        }
        return REGISTER_SUCCESS_VIEW_PATH;
    }

    @GetMapping("/register")
    public String registerForm() {
        return REGISTER_FORM_VIEW_PATH;
    }

    @GetMapping("/verify")
    public String verify(String email, String token, Model model) {
        Optional<Account> account = userService.verify(email, token);
        model.addAttribute("account", account);
        return VERIFY_VIEW_PATH;
    }
}
