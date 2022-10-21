package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class MemberController {

    private static final String MEMBER_PATH = "/WEB-INF/jsp/member.jsp";

    @RequestMapping("/member")
    public void member(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
        String username = (String) req.getSession().getAttribute("login");
        List<Message> messages = userService.messages(username);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher(MEMBER_PATH).forward(req, resp);
    }
}
