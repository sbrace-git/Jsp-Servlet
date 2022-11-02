package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
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
    public String member(Model model, Principal principal) {
        String name = principal.getName();
        List<Message> messages = userService.messages(name);
        model.addAttribute("messages", messages);
        return MEMBER_VIEW_PATH;
    }

    @RequestMapping(value = "/new_message", method = RequestMethod.POST)
    public String newMessage(String blabla, Model model, Principal principal) {
        if (blabla.length() > 140 || blabla.length() == 0) {
            List<Message> messages = userService.messages(principal.getName());
            model.addAttribute("messages", messages);
            return MEMBER_VIEW_PATH;
        }
        userService.addMessage(principal.getName(), blabla);
        return REDIRECT_MEMBER_PATH;
    }

    @RequestMapping(value = "/del_message", method = RequestMethod.POST)
    public String delMessage(String millis, Principal principal) {
        if (null != millis) {
            userService.deleteMessage(principal.getName(), millis);
        }
        return REDIRECT_MEMBER_PATH;
    }

}
