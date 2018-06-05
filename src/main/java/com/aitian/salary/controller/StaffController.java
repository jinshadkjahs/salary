package com.aitian.salary.controller;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.controller.response.BaseResponse;
import com.aitian.salary.model.SalaryMain;
import com.aitian.salary.model.User;
import com.aitian.salary.service.SalaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/staff")
public class StaffController {

    @Resource
    private SalaryService salaryService;

    @RequestMapping("/findSalary")
    @ResponseBody

    public BaseResponse findSalary(HttpServletRequest request, String salaryDate) throws Exception {
        BaseResponse br = new BaseResponse();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ConverterSystem.SESSION_USER_KEY);
        String empId = user.getEmpId();
        String user_type = user.getUserType();
        if (StringUtils.isNotBlank(empId) && StringUtils.isNotBlank(salaryDate)) {
            SalaryMain salaryInfo = salaryService.findSalary(empId, salaryDate);
            br.setData(salaryInfo);
            br.setData(user_type);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        } else {
            br.setCode(com.aitian.salary.Utils.ReponseCode.PARAMETER_NULL_ERROR);
            br.setMessage("The parameter is NULL!");
        }
        return br;
    }
}

