package cc.openhome.gossip.controller;

import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@WebServlet(urlPatterns = "/new_message",
        initParams = {
                @WebInitParam(name = "LOGIN_PATH", value = "index.html"),
                @WebInitParam(name = "MEMBER_PATH", value = "member"),
                @WebInitParam(name = "MEMBER_VIEW", value = "member.view")
        }
)
public class NewMessage extends HttpServlet {

    private String MEMBER_PATH;
    private String MEMBER_VIEW;

    private UserService userService;

    @Override
    public void init() {
        MEMBER_PATH = getInitParameter("MEMBER_PATH");
        MEMBER_VIEW = getInitParameter("MEMBER_VIEW");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("login");
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String blabla = req.getParameter("blabla");
        if (null == blabla || blabla.length() > 140 || blabla.length() == 0) {
            req.getRequestDispatcher(MEMBER_VIEW).forward(req,resp);
            return;
        }
        userService.addMessage(username,blabla);
        resp.sendRedirect(MEMBER_PATH);
    }
}
