package cc.openhome.gossip.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/member", "/member.view", "/new_message", "/del_message", "/logout"},
        initParams = {
                @WebInitParam(name = "LOGIN_PATH", value = "index.html")
        }
)
public class AccessFilter extends HttpFilter {
    private String LOGIN_PATH;

    @Override
    public void init() {
        LOGIN_PATH = getInitParameter("LOGIN_PATH");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (null == req.getSession().getAttribute("login")) {
            res.sendRedirect(LOGIN_PATH);
            return;
        }
        chain.doFilter(req, res);
    }
}
