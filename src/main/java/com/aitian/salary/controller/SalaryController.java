package com.aitian.salary.controller;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.Utils.ReponseCode;
import com.aitian.salary.controller.response.BaseResponse;
import com.aitian.salary.model.EmployeeSalary;
import com.aitian.salary.model.Salary;
import com.aitian.salary.service.SalaryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    private static Log log = LogFactory.getLog(SalaryController.class);
    @Resource
    private SalaryService salaryService;

    @RequestMapping(value = "/getSalarys")
    @ResponseBody
    public String getSalaryList(HttpServletRequest request) throws Exception {
        BaseResponse br = new BaseResponse();
        String empNme = request.getParameter("empNme");
        String salaryDate = request.getParameter("salaryDate");
        String empId = request.getParameter("salaryDate");
        String departIdStr = request.getParameter("departId");
        String empTypeStr = request.getParameter("empType");
        String pageNStr = request.getParameter("pageN");

        Integer departId = null;
        Integer empType = null;
        Integer pageN = null;

        boolean paramterIllegal = true;
        //参数验证
        if(StringUtils.isNotBlank(salaryDate)){
            if(!salaryDate.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]")){
                paramterIllegal = false;
            }
        }
        if(StringUtils.isNotBlank(departIdStr)){
            if (departIdStr.matches("[0-9]*")){
                departId =  Integer.valueOf(departIdStr);
            }else{
                paramterIllegal = false;
            }
        }
        if(StringUtils.isNotBlank(empTypeStr)){
            if (empTypeStr.matches("[0-9]*")){
                empType =  Integer.valueOf(empTypeStr);
            }else{
                paramterIllegal = false;
            }
        }
        if(StringUtils.isNotBlank(pageNStr)){

            if (pageNStr.matches("[0-9]*")){
                pageN = Integer.valueOf(pageNStr);
            }else{
                paramterIllegal = false;
            }
        }else {
            pageN = 1;
        }

        if (paramterIllegal){
            //验证通过
            PageInfo<EmployeeSalary> salaryPageInfo = salaryService.findEmpSalary(empNme,empId,salaryDate,departId,empType,pageN, ConverterSystem.PAGE_SIZE);
            br.setData(salaryPageInfo);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else {
            br.setCode(com.aitian.salary.Utils.ReponseCode.ILLEGAL_PARAMETER);
            br.setMessage("The parameter is ILLEGAL!");
        }
        return new ObjectMapper().writeValueAsString(br);
    }

    @RequestMapping(value = "/getSalary", method = RequestMethod.GET)
    @ResponseBody
    public String getSalary(HttpServletRequest request, String empId) throws Exception {
        BaseResponse br = new BaseResponse();
        if(StringUtils.isNotBlank(empId)){
            PageInfo<EmployeeSalary> salaryPageInfo = salaryService.findEmpSalary(null,empId,null,null,null,1, ConverterSystem.PAGE_SIZE);
            List<EmployeeSalary> salaries = salaryPageInfo.getList();
            if(salaries.size() != 0){
                br.setData(salaries.get(0));
            }
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else{
            br.setCode(com.aitian.salary.Utils.ReponseCode.PARAMETER_NULL_ERROR);
            br.setMessage("The parameter is NULL!");
        }
        return new ObjectMapper().writeValueAsString(br);
    }

    @RequestMapping(value = "/addSalary", method = RequestMethod.POST)
    @ResponseBody
    public String addSalary(HttpServletRequest request, List<Salary> salaryList) throws Exception {
        BaseResponse br = new BaseResponse();
        boolean paramterIllegal = true;
        //数据验证
        for (Salary salary: salaryList ) {
            //员工编号是否为空
            if (StringUtils.isBlank(salary.getEmpId())){
                paramterIllegal = false;
                break;
            }
            //工资类型是否存在
            if (ConverterSystem.ALL_SALARY_TYPE.get(salary.getSalaryType()) == null){
                paramterIllegal = false;
                break;
            }
        }
        if(paramterIllegal){
            salaryService.addSalaryList(salaryList);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else {
            br.setCode(com.aitian.salary.Utils.ReponseCode.ILLEGAL_PARAMETER);
            br.setMessage("The parameter is ILLEGAL!");
        }
        return new ObjectMapper().writeValueAsString(br);
    }

    @RequestMapping(value = "/updateSalary", method = RequestMethod.PUT)
    @ResponseBody
    public String updateSalary(HttpServletRequest request, List<Salary> salaryList) throws Exception {
        BaseResponse br = new BaseResponse();
        boolean paramterIllegal = true;
        //数据验证
        for (Salary salary: salaryList ) {
            //员工编号是否为空
            if (StringUtils.isBlank(salary.getEmpId())){
                paramterIllegal = false;
                break;
            }
            //工资类型是否存在
            if (ConverterSystem.ALL_SALARY_TYPE.get(salary.getSalaryType()) == null){
                paramterIllegal = false;
                break;
            }
        }
        if(paramterIllegal){
            salaryService.updateSalarys(salaryList);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else {
            br.setCode(com.aitian.salary.Utils.ReponseCode.ILLEGAL_PARAMETER);
            br.setMessage("The parameter is ILLEGAL!");
        }
        return new ObjectMapper().writeValueAsString(br);
    }

    @RequestMapping(value = "/deleteSalary", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteSalary(HttpServletRequest request, String empId) throws Exception {
        BaseResponse br = new BaseResponse();
        if(StringUtils.isNotBlank(empId)){
            salaryService.deleteSalaryByEmpId(empId);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else{
            br.setCode(com.aitian.salary.Utils.ReponseCode.PARAMETER_NULL_ERROR);
            br.setMessage("The parameter is NULL!");
        }
        return new ObjectMapper().writeValueAsString(br);
    }


    @RequestMapping(value = "/batchimport", method = RequestMethod.POST)
    public String getParamFromFileForAjax(HttpServletRequest request, String fileName) throws Exception  {
        BaseResponse br = new BaseResponse();
        br.setCode(ReponseCode.ILLEGAL_PARAMETER);
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        FileInputStream inputStream = null;

        //把Request强转成多部件请求对象
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        //根据文件名称获取文件对象
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile(fileName);

        log.info(" batchimport() start");

        String filename = commonsMultipartFile.getName();
        String originalFilename = commonsMultipartFile.getOriginalFilename();

        System.out.println("FileName = " + filename);
        System.out.println("originalFilename = " + originalFilename);


        inputStream = (FileInputStream) commonsMultipartFile.getInputStream();
        int[] arr = salaryService.batchImport(inputStream, fileName);
        // arr 三个 第一个错误类型   第二、三个错误行、列数
        if(arr.length == 0){
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else {
            br.setCode(ReponseCode.EXCEL_IMPORT_ERROR);
            br.setMessage("The file import error!");
            br.setData(arr);
        }
        return new ObjectMapper().writeValueAsString(br);
    }

}
