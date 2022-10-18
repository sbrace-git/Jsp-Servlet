package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.service.EmailService;
import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/forgot", initParams = {@WebInitParam(name = "FORGET_PATH", value = "/WEB-INF/jsp/forgot.jsp")})
public class ForgotController extends HttpServlet {

    private UserService userService;

    private EmailService emailService;

    private String FORGET_PATH;


    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
        emailService = (EmailService) servletContext.getAttribute("emailService");
        FORGET_PATH = getInitParameter("FORGET_PATH");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        Optional<Account> accountByNameEmail = userService.getAccountByNameEmail(name, email);
        accountByNameEmail.ifPresent(account -> emailService.passwordResetLink(account));
        req.setAttribute("email", email);
        req.getRequestDispatcher(FORGET_PATH).forward(req, resp);
    }
}
