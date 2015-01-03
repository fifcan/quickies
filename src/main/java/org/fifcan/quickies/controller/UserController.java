package org.fifcan.quickies.controller;

import org.fifcan.quickies.Menu;
import org.fifcan.quickies.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by philippe on 21.12.14.
 */
@Controller
public class UserController {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String get(Model model){
        model.addAttribute("menu", Menu.USER);
        model.addAttribute("users", this.mongoTemplate.findAll(User.class));
        model.addAttribute("user", new User());
        return "users";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user, Model model) {
        this.mongoTemplate.save(user);
        return "redirect:/users";
    }
}
