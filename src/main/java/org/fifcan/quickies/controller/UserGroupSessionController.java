package org.fifcan.quickies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by philippe on 21.12.14.
 */
@Controller
public class UserGroupSessionController {

    @RequestMapping(value="/sessions")
    public String index(){
        return "sessions";
    }
}
