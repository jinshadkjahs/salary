package com.aitian.salary.controller;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.Utils.ReponseCode;
import com.aitian.salary.controller.response.BaseResponse;
import com.aitian.salary.model.User;
import com.aitian.salary.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
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
    public String login(HttpServletRequest request, Model model) throws JsonProcessingException {
        BaseResponse br = new BaseResponse();
        String empId = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        if(StringUtils.isNotBlank(empId)&&StringUtils.isNotBlank(pwd)){
            User user = this.userService.findUserByLogin(empId, pwd);
            if(user != null){
                request.getSession().setAttribute(ConverterSystem.SESSION_USER_KEY,user);
                br.setCode(ReponseCode.REQUEST_SUCCESS);
            }else {
                br.setCode(ReponseCode.REQUEST_SUCCESS);
                br.setMessage("Login failed; Password or user error!");
            }
        }else {
            br.setCode(ReponseCode.PARAMETER_NULL_ERROR);
            br.setMessage("The parameter is null!");
        }
        return new ObjectMapper().writeValueAsString(br);
    }

}