package org.fifcan.quickies.controller;

import org.fifcan.quickies.Menu;
import org.fifcan.quickies.data.User;
import org.fifcan.quickies.data.Vote;
import org.fifcan.quickies.mongo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by philippe on 21.12.14.
 */
@Controller
public class UserAccountController {

    @Autowired
    protected UserDao userDao;

    @RequestMapping(value = "/userAccount", method = RequestMethod.GET)
    public String get(Model model){

        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        final User user = (User) userDao.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "userAccount";
    }

    @RequestMapping(value = "/userAccount/update", method = RequestMethod.POST)
    public String updateUser(Model model) {
//        this.mongoTemplate.save(user);
        return "redirect:/userAccount";
    }

    @RequestMapping(value = "/userAccount/updatePassword", method = RequestMethod.POST)
    public String updateUserPassword(Model model) {
//        this.mongoTemplate.save(user);
        return "redirect:/userAccount";
    }
}
