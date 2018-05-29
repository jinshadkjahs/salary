package com.aitian.salary.controller;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.model.User;
import com.aitian.salary.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public String toIndex(HttpServletRequest request, Model model) throws JsonProcessingException {
        String empId = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        User user = this.userService.findUserByLogin(empId, pwd);
        if(user != null){
            request.getSession().setAttribute(ConverterSystem.SESSION_USER_KEY,user);
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("loginInfo",user!=null);
        return new ObjectMapper().writeValueAsString(map);
    }

}