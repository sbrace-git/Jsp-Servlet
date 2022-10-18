package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/verify", initParams = @WebInitParam(name = "VERIFY_PATH", value = "/WEB-INF/jsp/verify.jsp"))
public class VerifyController extends HttpServlet {

    private UserService userService;
    private String VERIFY_PATH;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        VERIFY_PATH = getInitParameter("VERIFY_PATH");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String token = req.getParameter("token");
        Optional<Account> account = userService.verify(email, token);
        req.setAttribute("account", account);
        req.getRequestDispatcher(VERIFY_PATH).forward(req, resp);
    }
}
