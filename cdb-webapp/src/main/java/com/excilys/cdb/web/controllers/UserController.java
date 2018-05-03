package com.excilys.cdb.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
    
    @GetMapping("/forbidden")
    public ModelAndView handle() {
        ModelAndView model = new ModelAndView("403");
        return model;
    }
}
