package org.fifcan.quickies.controller;

import org.apache.log4j.Logger;
import org.fifcan.quickies.Menu;
import org.fifcan.quickies.data.Comment;
import org.fifcan.quickies.data.UserGroup;
import org.fifcan.quickies.data.UserGroupSession;
import org.fifcan.quickies.mongo.CommentDao;
import org.fifcan.quickies.mongo.SessionDao;
import org.fifcan.quickies.mongo.UserGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by philippe on 21.12.14.
 */
@Controller
public class UserGroupSessionController {

    public static final Logger log = Logger.getLogger(UserGroupSessionController.class);

    private Facebook facebook;

    private ConnectionRepository connectionRepository;

    @Inject
    public UserGroupSessionController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @Autowired
    SessionDao sessionDao;

    @Autowired
    UserGroupDao groupDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    protected MongoTemplate database;

    @RequestMapping(value="/userGroupSessions", method = RequestMethod.GET)
    public String index(Model model){

        model.addAttribute("menu", Menu.USER_GROUP_SESSION);

        model.addAttribute("userGroupSession", new UserGroupSession());

        if (connectionRepository != null){
            log.info("connectionRepository="+connectionRepository);
            MultiValueMap<String, Connection<?>> allConnections = connectionRepository.findAllConnections();
            for(String key : allConnections.keySet()){
                List<Connection<?>> connections = allConnections.get(key);
                for(Connection<?> conn : connections){
                    log.info(String.format("connexion: key=[%s] name=[%s] profile=[%s]", key, conn.getDisplayName(), conn.getProfileUrl()));
                }
            }
        }

        if (facebook.isAuthorized()) {
            model.addAttribute(facebook.userOperations().getUserProfile());
            PagedList<Post> homeFeed = facebook.feedOperations().getHomeFeed();
            model.addAttribute("feed", homeFeed);

        }



        return "userGroupSessions";
    }

    @RequestMapping(value="/userGroupSessions/add", method = RequestMethod.POST)
    public String add(@ModelAttribute UserGroupSession userGroupSession) {

        sessionDao.save(userGroupSession);

        return "redirect:/userGroupSessions";
    }

    @RequestMapping(value="/userGroupSessions/view/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable String id) {

        UserGroupSession userGroupSession = sessionDao.findSessionById(id);

        model.addAttribute("userGroupSession", userGroupSession);

        List<Comment> comments = this.database.findAll(Comment.class);

        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment(userGroupSession.getId()));

        return "userGroupSessions.view";
    }

    @RequestMapping(value="/userGroupSessions/update/{id}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable String id) {

        UserGroupSession userGroupSession = sessionDao.findSessionById(id);

        model.addAttribute("userGroupSession", userGroupSession);
        return "userGroupSessions.update";
    }

    @RequestMapping(value="/userGroupSessions/update", method = RequestMethod.POST)
    public String update(@ModelAttribute UserGroupSession userGroupSession) {

        this.sessionDao.save(userGroupSession);

        return "redirect:/userGroupSessions";
    }

    @RequestMapping(value="/userGroupSessions/addComment", method = RequestMethod.POST)
    public String addComment(@ModelAttribute Comment comment) {

        this.commentDao.save(comment);

        return "redirect:/userGroupSessions/view/" + comment.getUserGroupSession();
    }

    @RequestMapping(value="/userGroupSessions/remove/{id}", method = RequestMethod.GET)
    public String remove(Model model, @PathVariable String id) {

        UserGroupSession sessionRemoved = sessionDao.findSessionById(id);
        boolean removed = sessionDao.remove(id);

        model.addAttribute("userGroupSession", sessionRemoved);
        return "userGroupSessions.remove";
    }

    @RequestMapping(value="/userGroupSessions/remove", method = RequestMethod.POST)
    public String remove(@ModelAttribute UserGroupSession userGroupSession) {

        boolean removed = sessionDao.remove(userGroupSession.getId());
        return "redirect:/userGroupSessions";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @ModelAttribute("allUserGroupSessions")
    public List<UserGroupSession> populateUserGroupSessions() {

        return sessionDao.listSessions();
    }

    @ModelAttribute("allUserGroups")
    public List<UserGroup> populateUserGroups() {
        return groupDao.listGroups();
    }

    @ModelAttribute("nextSessions")
    public List<UserGroupSession> populateNextSessions() {

        List<UserGroupSession> sessions = sessionDao.findNextSessions();

        // Sort by date
        sessions = sessions.stream()
                .sorted((s1, s2) -> s1.getEventDate().compareTo(s2.getEventDate()))
                .limit(3)
                .collect(Collectors.toList());

        return sessions;
    }

}
