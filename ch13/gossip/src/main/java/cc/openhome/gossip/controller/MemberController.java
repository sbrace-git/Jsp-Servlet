package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private UserService userService;

    @Value("${member.view.path}")
    private String MEMBER_VIEW_PATH;

    @Value("${redirect.member.path}")
    private String REDIRECT_MEMBER_PATH;

    @RequestMapping("/member")
    public String member(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("login");
        List<Message> messages = userService.messages(username);
        req.setAttribute("messages", messages);
        return MEMBER_VIEW_PATH;
    }

    @RequestMapping(value = "/new_message", method = RequestMethod.POST)
    public String newMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("login");
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String blabla = req.getParameter("blabla");
        if (null == blabla || blabla.length() > 140 || blabla.length() == 0) {
            return MEMBER_VIEW_PATH;
        }
        userService.addMessage(username, blabla);
        return REDIRECT_MEMBER_PATH;
    }

    @RequestMapping(value = "/del_message", method = RequestMethod.POST)
    public String delMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = (String) req.getSession().getAttribute("login");
        String millis = req.getParameter("millis");
        if (null != millis) {
            userService.deleteMessage(username, millis);
        }
        return REDIRECT_MEMBER_PATH;
    }

}
