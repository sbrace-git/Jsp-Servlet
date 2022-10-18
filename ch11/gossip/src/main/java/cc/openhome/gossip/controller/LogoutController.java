package cc.openhome.gossip.controller;

import cc.openhome.gossip.constant.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout",
        initParams = {
                @WebInitParam(name = "LOGIN_PATH", value = "/gossip")
        }
)
@ServletSecurity(
        @HttpConstraint(rolesAllowed = Role.member)
)
public class LogoutController extends HttpServlet {
    private static String LOGIN_PATH;

    @Override
    public void init() {
        LOGIN_PATH = getInitParameter("LOGIN_PATH");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getSession().invalidate();
        req.logout();
        resp.sendRedirect(LOGIN_PATH);
    }
}
