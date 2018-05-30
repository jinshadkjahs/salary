package com.aitian.salary.controller;

import com.aitian.salary.model.Salary;
import com.aitian.salary.service.SalaryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    private static Log log = LogFactory.getLog(SalaryController.class);
    @Resource
    private SalaryService salaryService;

    @RequestMapping(value = "/getSalarys", method = RequestMethod.GET)
    @ResponseBody
    public String getSalaryList(HttpServletRequest request) throws JsonProcessingException {

        return null;
    }

    @RequestMapping(value = "/getSalary", method = RequestMethod.GET)
    @ResponseBody
    public String getSalary(HttpServletRequest request, Integer empid) throws JsonProcessingException {

        return null;
    }

    @RequestMapping(value = "/addSalary", method = RequestMethod.POST)
    @ResponseBody
    public String addSalary(HttpServletRequest request, List<Salary> salaryList) throws JsonProcessingException {

        return null;
    }

    @RequestMapping(value = "/updateSalary", method = RequestMethod.PUT)
    @ResponseBody
    public String updateSalary(HttpServletRequest request, List<Salary> salaryList) throws JsonProcessingException {

        return null;
    }

    @RequestMapping(value = "/deleteSalary", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteSalary(HttpServletRequest request, Integer empid) throws JsonProcessingException {

        return null;
    }


    @RequestMapping(value = "/batchimport", method = RequestMethod.POST)
    public String batchimport(@RequestParam(value="filename") MultipartFile file,
                              HttpServletRequest request,HttpServletResponse response) throws IOException {
        log.info(" batchimport() start");
        //判断文件是否为空
        if(file==null) return null;
        //获取文件名
        String name=file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(name==null || ("").equals(name) && size==0) return null;

        //批量导入。参数：文件名，文件。
        boolean b = salaryService.batchImport(name,file);
        if(b){
            String Msg ="批量导入EXCEL成功！";
            request.getSession().setAttribute("msg",Msg);
        }else{
            String Msg ="批量导入EXCEL失败！";
            request.getSession().setAttribute("msg",Msg);
        }
        return "Customer/addCustomer3";
    }
}