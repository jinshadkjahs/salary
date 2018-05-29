package com.aitian.salary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author yisicheng 2018-05-29
 * 静态页面跳转
 */
@Controller
public class IndexController {

    /**
     *
     */
    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @RequestMapping("/pages/*")
    public String main(HttpServletRequest request) {
        return request.getRequestURI();
    }

}