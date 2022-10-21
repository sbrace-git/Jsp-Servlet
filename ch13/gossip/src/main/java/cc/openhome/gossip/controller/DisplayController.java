package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
public class DisplayController {

    @Autowired
    private UserService userService;

    private static final String INDEX_VIEW_PATH = "/WEB-INF/jsp/index.jsp";
    private static final String USER_VIEW_PATH = "/WEB-INF/jsp/user.jsp";

    @RequestMapping("")
    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Message> newMessageList = userService.newMessageList();
        req.setAttribute("newMessageList", newMessageList);
        req.getRequestDispatcher(INDEX_VIEW_PATH).forward(req, resp);
    }

    @GetMapping("/user/{username}")
    private void user(@PathVariable String username,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("username", username);
        if (userService.userExist(username)) {
            List<Message> messages = userService.messages(username);
            req.setAttribute("messages", messages);
        } else {
            req.setAttribute("errors", Collections.singletonList(String.format("%s, 还没有发表信息", username)));
        }
        req.getRequestDispatcher(USER_VIEW_PATH).forward(req, resp);
    }
}
