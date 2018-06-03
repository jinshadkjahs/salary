package com.aitian.salary.controller;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.Utils.ReponseCode;
import com.aitian.salary.controller.response.BaseResponse;
import com.aitian.salary.model.Employee;
import com.aitian.salary.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/emp")
public class EmployeeController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/queryEmpInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object queryEmpInfo(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        BaseResponse br = new BaseResponse();
        String empId = request.getParameter("empNo");
        String empName = request.getParameter("empName");
        String pageNStr = request.getParameter("pageNum");
        boolean paramterIllegal = true;
        Integer pageN = null;
        if(!StringUtils.isEmpty(pageNStr)){

            if (pageNStr.matches("[0-9]*")){
                pageN = Integer.valueOf(pageNStr);
            }else{
                paramterIllegal = false;
            }
        }else {
            pageN = 1;
        }
        if(paramterIllegal){
            PageInfo<Employee> employee = employeeService.findEmployee(empId, empName, pageN, ConverterSystem.PAGE_SIZE);
            br.setData(employee);
            br.setCode(ReponseCode.REQUEST_SUCCESS);
        }else{
            br.setCode(com.aitian.salary.Utils.ReponseCode.ILLEGAL_PARAMETER);
            br.setMessage("The parameter is ILLEGAL!");
        }

        return br;
    }

    @RequestMapping(value = "/deleteEmp",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteEmp(HttpServletRequest request){
        BaseResponse br = new BaseResponse();
        String empId = request.getParameter("empId");
        try{
            List<Employee> employees = employeeService.queryEmpAndUser(empId);
            if(employees.size()>0){
                employeeService.deleteEmp(empId);
                br.setCode(ReponseCode.REQUEST_SUCCESS);
                br.setMessage("删除成功！");
            }else{
                br.setCode(ReponseCode.REQUEST_ERROR);
                br.setMessage("员工删除失败:\n该员工在用户表中无对应信息！");
                return br;
            }

        }catch (Exception ex){
            br.setCode(ReponseCode.REQUEST_ERROR);
            br.setMessage("删除错误：\n"+ex.getMessage());
            return br;
        }
        return br;
    }

}
