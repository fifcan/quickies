package org.fifcan.quickies.controller;

import org.fifcan.quickies.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by philippe on 21.12.14.
 */
@Controller
public class HomeController {

    @RequestMapping(value="/")
    public String index(){
        return "redirect:/userGroupSessions";
    }

}
