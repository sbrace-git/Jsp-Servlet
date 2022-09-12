package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("")
public class IndexController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<Message> newMessageList = userService.newMessageList();
        req.setAttribute("newMessageList", newMessageList);
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }
}
