package com.aitian.salary.controller;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.Utils.ReponseCode;
import com.aitian.salary.controller.response.BaseResponse;
import com.aitian.salary.model.Employee;
import com.aitian.salary.model.User;
import com.aitian.salary.service.EmployeeService;
import com.aitian.salary.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private EmployeeService employeeService;

    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request, Model model) throws Exception {
        BaseResponse br = new BaseResponse();
        String empId = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        if(StringUtils.isNotBlank(empId)&&StringUtils.isNotBlank(pwd)){
            User user = this.userService.findUserByLogin(empId, pwd);
            if(user != null){
                Employee employee = employeeService.queryEmpForUser(empId);
                if(employee != null){
                    user.setEmployee(employee);
                }
                request.getSession().setAttribute(ConverterSystem.SESSION_USER_KEY,user);
                br.setCode(ReponseCode.REQUEST_SUCCESS);
            }else {
                br.setCode(ReponseCode.PWD_OR_NAME_ERROR);
                br.setMessage("Login failed; Password or user error!");
            }
        }else {
            br.setCode(ReponseCode.PARAMETER_NULL_ERROR);
            br.setMessage("The parameter is null!");
        }
        return new ObjectMapper().writeValueAsString(br);
    }


    @RequestMapping(value = "/getLoginUser",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getLoginUser(HttpServletRequest request) throws JsonProcessingException {
        BaseResponse br = new BaseResponse();
        User user = (User) request.getSession().getAttribute(ConverterSystem.SESSION_USER_KEY);
        if(user != null){
            br.setData(user);
            br.setCode(ReponseCode.REQUEST_SUCCESS);
        }else {
            br.setCode(ReponseCode.NOT_LOGINED);
            br.setMessage("not Login!");
        }
        return new ObjectMapper().writeValueAsString(br);
    }

    @RequestMapping(value = "/signOut",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String signOut(HttpServletRequest request) throws JsonProcessingException {
        BaseResponse br = new BaseResponse();
        User user = (User) request.getSession().getAttribute(ConverterSystem.SESSION_USER_KEY);
        if(user != null){
            request.getSession().removeAttribute(ConverterSystem.SESSION_USER_KEY);
            br.setCode(ReponseCode.REQUEST_SUCCESS);
        }
        return new ObjectMapper().writeValueAsString(br);
    }
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updatePassword(HttpServletRequest request) throws JsonProcessingException {
        BaseResponse br = new BaseResponse();
        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        if(StringUtils.isBlank(oldPass) || StringUtils.isBlank(newPass)){
            br.setCode(ReponseCode.PARAMETER_NULL_ERROR);
            return br;
        }
        User user = (User) request.getSession().getAttribute(ConverterSystem.SESSION_USER_KEY);
        if(user != null){
            if(user.getPassword().equals(oldPass)){
                user.setPassword(newPass);
                userService.updateUser(user);
                request.getSession().removeAttribute(ConverterSystem.SESSION_USER_KEY);
                br.setCode(ReponseCode.REQUEST_SUCCESS);
            }else {
                br.setCode(ReponseCode.PASSWORD_ERROR);
            }
        }
        return br;
    }
}