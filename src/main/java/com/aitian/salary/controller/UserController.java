package com.aitian.salary.controller;

import com.aitian.salary.model.User;
import com.aitian.salary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public User toIndex(HttpServletRequest request, Model model){
        String empId = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        User user = this.userService.findUserByLogin(empId, pwd);
        return user;
    }

}