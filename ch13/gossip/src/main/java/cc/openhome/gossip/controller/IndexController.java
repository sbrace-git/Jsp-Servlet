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

@WebServlet(urlPatterns = "", initParams = @WebInitParam(name = "INDEX_PATH", value = "/WEB-INF/jsp/index.jsp"))
public class IndexController extends HttpServlet {
    private UserService userService;
    private String INDEX_PATH;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        INDEX_PATH = getInitParameter("INDEX_PATH");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Message> newMessageList = userService.newMessageList();
        req.setAttribute("newMessageList", newMessageList);
        req.getRequestDispatcher(INDEX_PATH).forward(req, resp);
    }
}
