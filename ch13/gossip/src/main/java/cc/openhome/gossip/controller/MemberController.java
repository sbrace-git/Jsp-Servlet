package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.IOException;
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
    public String member(@SessionAttribute("login") String username, Model model) {
        List<Message> messages = userService.messages(username);
        model.addAttribute("messages", messages);
        return MEMBER_VIEW_PATH;
    }

    @RequestMapping(value = "/new_message", method = RequestMethod.POST)
    public String newMessage(@SessionAttribute("login") String username, String blabla, Model model) {
        if (blabla.length() > 140 || blabla.length() == 0) {
            List<Message> messages = userService.messages(username);
            model.addAttribute("messages", messages);
            return MEMBER_VIEW_PATH;
        }
        userService.addMessage(username, blabla);
        return REDIRECT_MEMBER_PATH;
    }

    @RequestMapping(value = "/del_message", method = RequestMethod.POST)
    public String delMessage(@SessionAttribute("login") String username, String millis) {
        if (null != millis) {
            userService.deleteMessage(username, millis);
        }
        return REDIRECT_MEMBER_PATH;
    }

}
