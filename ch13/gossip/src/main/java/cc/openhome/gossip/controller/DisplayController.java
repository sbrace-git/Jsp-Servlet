package cc.openhome.gossip.controller;

import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class DisplayController {

    @Autowired
    private UserService userService;

    @Value("${index.view.path}")
    private String INDEX_VIEW_PATH;
    @Value("${user.view.path}")
    private String USER_VIEW_PATH;

    @RequestMapping("")
    private String index(Model model) {
        List<Message> newMessageList = userService.newMessageList();
        model.addAttribute("newMessageList", newMessageList);
        return INDEX_VIEW_PATH;
    }

    @GetMapping("/user/{username}")
    private String user(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        if (userService.userExist(username)) {
            List<Message> messages = userService.messages(username);
            model.addAttribute("messages", messages);
        } else {
            model.addAttribute("errors", Collections.singletonList(String.format("%s, 还没有发表信息", username)));
        }
        return USER_VIEW_PATH;
    }
}
