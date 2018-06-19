package com.aitian.salary.controller;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.Utils.EmpConstant;
import com.aitian.salary.Utils.ReponseCode;
import com.aitian.salary.controller.response.BaseResponse;
import com.aitian.salary.model.Employee;
import com.aitian.salary.model.ImportEmpInfo;
import com.aitian.salary.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String empType = request.getParameter("empType");
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
            PageInfo<Employee> employee = employeeService.findEmployee(empId, empName,empType, pageN, ConverterSystem.PAGE_SIZE);
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
            br.setMessage("删除错误：\n"+ex. getMessage());
            return br;
        }
        return br;
    }

    @RequestMapping(value = "/intoModifyEmp/*")
    public String intoModifyEmp(HttpServletRequest request){
        return "employee/modify";
    }

    @RequestMapping(value="/getEmp")
    @ResponseBody
    public Object getEmp(HttpServletRequest request,String empId){
        BaseResponse br = new BaseResponse();
        try{
            List<Employee> emp = this.employeeService.getEmp(empId);
            if(emp.size()==1){
                br.setData(emp.get(0));
                br.setCode(ReponseCode.REQUEST_SUCCESS);
            }else{
                br.setCode(ReponseCode.REQUEST_ERROR);
                String str = "";
                for(int i=0; i<emp.size(); i++){
                    if(i<emp.size()){
                        str+=emp.get(i).getEmpName()+",";
                    }else {
                        str+=emp.get(i).getEmpName();
                    }
                }
                br.setMessage("员工数据获取错误，加载到多个员工："+str);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return br;
    }

    @RequestMapping(value = "/modifyEmp",method = RequestMethod.PUT)
    @ResponseBody
    public Object modifyEmp(HttpServletRequest request,Employee employee){
        BaseResponse br = new BaseResponse();
        Map<String,String[]> map = request.getParameterMap();
        String empId = employee.getEmpId();
        String empType = employee.getEmpType();
        if(!StringUtils.isEmpty(empType)&&(EmpConstant.STR_OFFICIAL_EMPLOYEE.equals(empType))){
            employee.setEmpType(EmpConstant.OFFICIAL_EMPLOYEE);
        }else if(!StringUtils.isEmpty(empType) && (EmpConstant.STR_NON_OFFICIAL_EMPLOYEE.equals(empType))){
            employee.setEmpType(EmpConstant.NON_OFFICIAL_EMPLOYEE);
        }
        Employee employeeOld = this.employeeService.queryEmpForUser(empId);
        if(employee.getEmpName().equals(employeeOld.getEmpName())
                && employee.getEmpType().equals(employeeOld.getEmpType())
                && employee.getEmpPhone().equals(employeeOld.getEmpPhone())
                && employee.getEmpCardNum().equals(employeeOld.getEmpCardNum())
                && employee.getBaseSalary().equals(employeeOld.getBaseSalary())){
            br.setCode(ReponseCode.REQUEST_ERROR);
            br.setMessage("修改失败，员工信息未做更改！");
        }else{
            Date date = new Date();
            Timestamp updateTime = new Timestamp(date.getTime());
            employee.setUpdateTime(updateTime);
            this.employeeService.modifyEmp(employee);
            br.setCode(ReponseCode.REQUEST_SUCCESS);
            br.setMessage("员工信息更新成功！");
        }
        return br;
    }

    @RequestMapping(value = "/importEmp",method = {RequestMethod.POST})
    @ResponseBody
    public Object importEmp(HttpServletRequest request) throws Exception {
        BaseResponse br = new BaseResponse();
        MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> multiFileMap = multiReq.getMultiFileMap();
        if(multiFileMap.size()>0){
            for(String key : multiFileMap.keySet()){
                List<MultipartFile> multipartFiles = multiFileMap.get(key);
                if(multipartFiles.size()>0){
                    MultipartFile multipartFile = multipartFiles.get(0);
                    if(!multipartFile.isEmpty())    {
                        String originalFilename = multipartFile.getOriginalFilename();
                        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                        if(!Arrays.asList(ConverterSystem.EXTENSIONPERMIT).contains(fileExtension)){
                            br.setCode(ReponseCode.NOT_ALLOW_FILE);
                            break;
                        }
                        String newfile = request.getSession().getServletContext().getRealPath("");
                        if(!new File(newfile+ File.separator+"upload").exists()){
                            new File(newfile+ File.separator+"upload").mkdirs();
                        }
                        File fileUpload = new File(newfile+ File.separator+"upload"+File.separator+multipartFile.getOriginalFilename());
                        FileCopyUtils.copy(multipartFile.getInputStream(),new FileOutputStream(fileUpload));

                        ImportEmpInfo importEmpInfo = employeeService.importEmp(new FileInputStream(fileUpload), originalFilename);
                        if(importEmpInfo!=null){
                            br.setCode(ReponseCode.REQUEST_SUCCESS);
                            br.setData(importEmpInfo);
                        }
                    }

                }else{
                    br.setCode(ReponseCode.HAS_NOT_FILE);
                }
                break;
            }
        }else{
            br.setCode(ReponseCode.HAS_NOT_FILE);
        }
        return br;
    }

    @ResponseBody
    @RequestMapping(value = "/exportEmpModel",method = RequestMethod.GET)
    public void exportEmpModel(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String importType = request.getParameter("importType");
        String fileName = "其他导入模板.xlsx";
        if(EmpConstant.OFFICIAL_EMPLOYEE.equals(importType)){
            fileName="正式工导入模板.xlsx";
        }else{
            fileName="合同工导入模板.xlsx";
        }
        response.setContentType("octets/stream");
        response.addHeader("Content-Disposition", "attachment;filename="+new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
        XSSFWorkbook hssfWorkbook = this.employeeService.exportEmpModel(importType);
        hssfWorkbook.write(response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
