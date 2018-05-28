package com.aitian.salary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class IndexController {

    /**
     *
     */
    @RequestMapping("/index")
    public String helloHtml() {
        return "/index";
    }
}