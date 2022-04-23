package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//@WebServlet(urlPatterns = "/member",
//        initParams = {
//                @WebInitParam(name = "MEMBER_PATH", value = "/WEB-INF/jsp/member.jsp")
//        }
//)
public class Member extends HttpServlet {

    private String MEMBER_PATH;

    private UserService userService;

    @Override
    public void init() {
        MEMBER_PATH = getInitParameter("MEMBER_PATH");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("login");
        List<Message> messages = userService.messages(username);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher(MEMBER_PATH).forward(req, resp);
    }
}
