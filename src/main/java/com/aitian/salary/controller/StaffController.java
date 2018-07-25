package com.aitian.salary.controller;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.controller.response.BaseResponse;
import com.aitian.salary.model.BonusInfo;
import com.aitian.salary.model.SalaryMain;
import com.aitian.salary.model.User;
import com.aitian.salary.service.SalaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;


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
        String salaryId = request.getParameter("salaryId");
        if(StringUtils.isNotBlank(salaryId)){
            SalaryMain salaryInfo = salaryService.findSalaryByPk(Integer.parseInt(salaryId));
            salaryInfo.getSalaryTypeEmpList().sort((p1, p2) -> p1.getSalaryType().compareTo(p2.getSalaryType()));
            List<BonusInfo> bonusInfo = salaryService.findBonusInfo(salaryInfo.getSalaryId());
            Map result = new HashMap<>();
            result.put("salaryInfo", salaryInfo);
            result.put("emp_type", salaryInfo.getEmployee().getEmpType());
            result.put("bonusInfo", bonusInfo);
            br.setData(result);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
            return br;
        }
        User user = (User) session.getAttribute(ConverterSystem.SESSION_USER_KEY);
        String empId = user.getEmpId();
        String emp_type = user.getEmployee().getEmpType();
        if (StringUtils.isNotBlank(empId) && StringUtils.isNotBlank(salaryDate)) {
            SalaryMain salaryInfo = salaryService.findSalary(empId, salaryDate);
            if(salaryInfo != null){
                salaryInfo.getSalaryTypeEmpList().sort((p1, p2) -> p1.getSalaryType().compareTo(p2.getSalaryType()));
                List<BonusInfo> bonusInfo = salaryService.findBonusInfo(salaryInfo.getSalaryId());
                Map result = new HashMap<>();
                result.put("salaryInfo", salaryInfo);
                result.put("emp_type", emp_type);
                result.put("bonusInfo", bonusInfo);
    //            result.put("salaryTypes", "0".equals(emp_type)?ConverterSystem.FORMAL_SALARY_TYPE:ConverterSystem.PACT_SALARY_TYPE);
                br.setData(result);
                br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
            }else {
                br.setCode(com.aitian.salary.Utils.ReponseCode.NOT_HAS_DATA);
            }

        } else {
            br.setCode(com.aitian.salary.Utils.ReponseCode.PARAMETER_NULL_ERROR);
            br.setMessage("The parameter is NULL!");
        }
        return br;
    }
}

