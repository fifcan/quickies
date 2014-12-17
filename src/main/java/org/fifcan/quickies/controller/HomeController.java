package org.fifcan.quickies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by philippe on 16.12.14.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    String index() {
        return "index";
    }

}
