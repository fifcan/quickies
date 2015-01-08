package org.fifcan.quickies.controller;

import org.apache.log4j.Logger;
import org.fifcan.quickies.Menu;
import org.fifcan.quickies.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by philippe on 21.12.14.
 */
@Controller
public class UserGroupSessionController {

    public static final Logger log = Logger.getLogger(UserGroupSessionController.class);

    @Autowired
    protected MongoTemplate database;

    @RequestMapping(value="/userGroupSessions", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("menu", Menu.USER_GROUP_SESSION);

        model.addAttribute("userGroupSession", new UserGroupSession());

        return "userGroupSessions";
    }

    @RequestMapping(value="/userGroupSessions/add", method = RequestMethod.POST)
    public String add(@ModelAttribute UserGroupSession userGroupSession) {
        this.save(userGroupSession);
        return "redirect:/userGroupSessions";
    }

    @RequestMapping(value="/userGroupSessions/view/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable String id) {
        UserGroupSession userGroupSession = this.database.findById(id, UserGroupSession.class);
        model.addAttribute("userGroupSession", userGroupSession);

        List<Comment> comments = this.database.findAll(Comment.class);
        model.addAttribute("comments", comments);

        model.addAttribute("comment", new Comment(userGroupSession.getId()));

        return "userGroupSessions.view";
    }

    @RequestMapping(value="/userGroupSessions/update/{id}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable String id) {
        UserGroupSession userGroupSession = this.database.findById(id, UserGroupSession.class);
        model.addAttribute("userGroupSession", userGroupSession);
        return "userGroupSessions.update";
    }

    @RequestMapping(value="/userGroupSessions/update", method = RequestMethod.POST)
    public String update(@ModelAttribute UserGroupSession userGroupSession) {
        this.save(userGroupSession);
        return "redirect:/userGroupSessions";
    }

    @RequestMapping(value="/userGroupSessions/addComment", method = RequestMethod.POST)
    public String addComment(@ModelAttribute Comment comment) {
        this.save(comment);
        return "redirect:/userGroupSessions/view/" + comment.getUserGroupSession();
    }

    @RequestMapping(value="/userGroupSessions/remove/{id}", method = RequestMethod.GET)
    public String remove(Model model, @PathVariable String id) {
        UserGroupSession userGroupSession = this.database.findById(id, UserGroupSession.class);
        model.addAttribute("userGroupSession", userGroupSession);
        return "userGroupSessions.remove";
    }

    @RequestMapping(value="/userGroupSessions/remove", method = RequestMethod.POST)
    public String remove(@ModelAttribute UserGroupSession userGroupSession) {
        UserGroupSession userGroupSessionDB = this.database.findById(userGroupSession.getId(), UserGroupSession.class);
        this.database.remove(userGroupSessionDB);
        return "redirect:/userGroupSessions";
    }

    private void save(AbstractData data) {
        log.info("save " + data);
        this.database.save(data);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @ModelAttribute("allUserGroupSessions")
    public List<UserGroupSession> populateUserGroupSessions() {
        return this.database.findAll(UserGroupSession.class);
    }

    @ModelAttribute("allUserGroups")
    public List<UserGroup> populateUserGroups() {
        return this.database.findAll(UserGroup.class);
    }

}
