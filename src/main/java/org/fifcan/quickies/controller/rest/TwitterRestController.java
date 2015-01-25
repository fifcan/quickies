package org.fifcan.quickies.controller.rest;

import org.fifcan.quickies.twitter.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by deft on 21/01/2015.
 */
@RestController
public class TwitterRestController {

    @Autowired
    TwitterService twitterService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/twitter/UGQuickie")
    public List getUGQuickieTweets() {

        return twitterService.getAllTweets();
    }

}
