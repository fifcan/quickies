package org.fifcan.quickies.controller;

import org.fifcan.quickies.data.User;
import org.fifcan.quickies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by philippe on 21.12.14.
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String get(Model model){
        model.addAttribute("users", repository.findAll());
        model.addAttribute("user", new User());
        return "users";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user, Model model) {
        System.out.println(user);
        repository.save(user);

        model.addAttribute("user", new User());

        model.addAttribute("users", repository.findAll());

        return "users";
    }
}
