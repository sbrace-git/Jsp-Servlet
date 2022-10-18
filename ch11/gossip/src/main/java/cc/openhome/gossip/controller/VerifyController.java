package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/verify")
public class VerifyController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String token = req.getParameter("token");
        Optional<Account> account = userService.verify(email, token);
        req.setAttribute("account", account);
        req.getRequestDispatcher("/WEB-INF/jsp/verify.jsp").forward(req, resp);
    }
}
