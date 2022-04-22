package cc.openhome.gossip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout",
        initParams = {
                @WebInitParam(name = "LOGIN_PATH", value = "index.html")
        }
)
public class Logout extends HttpServlet {

    private String LOGIN_PATH;

    @Override
    public void init() {
        LOGIN_PATH = getInitParameter("LOGIN_PATH");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(LOGIN_PATH);
    }
}
