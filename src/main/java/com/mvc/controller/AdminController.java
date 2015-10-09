package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * A fake admin controller that does nothing fancy
 * @author Jerome
 *
 */
@Controller
public class AdminController {

    @RequestMapping(value="/admin", method=RequestMethod.GET)
    public String admin() {
        return "admin";
    }
}
