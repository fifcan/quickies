package org.fifcan.quickies.controller;

import org.fifcan.quickies.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by philippe on 21.12.14.
 */
@Controller
public class UserGroupSessionController {

    @RequestMapping(value="/userGroupSessions")
    public String index(Model model){
        model.addAttribute("menu", Menu.USER_GROUP_SESSION);
        return "userGroupSessions";
    }
}
