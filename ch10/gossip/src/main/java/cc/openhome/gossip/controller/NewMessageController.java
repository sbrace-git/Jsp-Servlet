package cc.openhome.gossip.controller;

import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(urlPatterns = "/new_message",
        initParams = {
                @WebInitParam(name = "LOGIN_PATH", value = "/gossip"),
                @WebInitParam(name = "MEMBER_PATH", value = "member")
        })
@ServletSecurity(
        @HttpConstraint(rolesAllowed = "member")
)
public class NewMessageController extends HttpServlet {
    private String MEMBER_PATH;

    private UserService userService;

    @Override
    public void init() {
        MEMBER_PATH = getInitParameter("MEMBER_PATH");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("login");
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String blabla = req.getParameter("blabla");
        if (null == blabla || blabla.length() > 140 || blabla.length() == 0) {
            req.getRequestDispatcher(MEMBER_PATH).forward(req, resp);
            return;
        }
        userService.addMessage(username, blabla);
        resp.sendRedirect(MEMBER_PATH);
    }
}
