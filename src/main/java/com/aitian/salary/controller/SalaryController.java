package com.aitian.salary.controller;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.Utils.ReponseCode;
import com.aitian.salary.controller.response.BaseResponse;
import com.aitian.salary.model.Employee;
import com.aitian.salary.model.SalaryMain;
import com.aitian.salary.model.SalaryTypeEmp;
import com.aitian.salary.service.SalaryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.net.www.protocol.file.FileURLConnection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    private static Log log = LogFactory.getLog(SalaryController.class);
    @Resource
    private SalaryService salaryService;

    @RequestMapping(value = "/getSalarys",method =RequestMethod.GET)
    @ResponseBody
    public BaseResponse getSalaryList(HttpServletRequest request) throws Exception {
        BaseResponse br = new BaseResponse();
        String empNme = request.getParameter("empName");
        String salaryDate = request.getParameter("salaryDate");
        String empId = request.getParameter("empId");
        String departIdStr = request.getParameter("departId");
        String empTypeStr = request.getParameter("empType");
        String pageNStr = request.getParameter("pageN");

        Integer departId = null;
        Integer empType = null;
        Integer pageN = null;

        if("-".equals(salaryDate)) salaryDate = null;

        boolean paramterIllegal = true;
        //参数验证
        if(StringUtils.isNotBlank(salaryDate)){
            if(!(salaryDate.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]")||salaryDate.matches("[0-9][0-9][0-9][0-9]-")||salaryDate.matches("-[0-9][0-9]"))){
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
            PageInfo<SalaryMain> salaryPageInfo = salaryService.findSalarys(empNme,empId,salaryDate,departId,empType,pageN, ConverterSystem.PAGE_SIZE);
            br.setData(salaryPageInfo);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else {
            br.setCode(com.aitian.salary.Utils.ReponseCode.ILLEGAL_PARAMETER);
            br.setMessage("The parameter is ILLEGAL!");
        }
        return br;
    }

    @RequestMapping(value = "/getSalary", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getSalary(HttpServletRequest request, String empId, String salaryDate,String salaryId) throws Exception {
        BaseResponse br = new BaseResponse();
        if((StringUtils.isNotBlank(empId)&&StringUtils.isNotBlank(salaryDate))||StringUtils.isNotBlank(salaryId)){
            SalaryMain salary = null;
            if(StringUtils.isNotBlank(salaryId)){
                salary = salaryService.findSalaryByPk(Integer.parseInt(salaryId));
            }else {
                salary = salaryService.findSalary(empId,salaryDate);
            }
            br.setData(salary);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else{
            br.setCode(com.aitian.salary.Utils.ReponseCode.PARAMETER_NULL_ERROR);
            br.setMessage("The parameter is NULL!");
        }
        return br;
    }

    @RequestMapping(value = "/addSalary", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addSalary(HttpServletRequest request, SalaryMain salaryMain) throws Exception {
        BaseResponse br = new BaseResponse();
        boolean paramterIllegal = true;
        String grossPayStr = request.getParameter("grossPay_dob");
        String netPayrollStr = request.getParameter("netPayroll_dob");
        if(StringUtils.isBlank(salaryMain.getEmpId()) || StringUtils.isBlank(salaryMain.getSalaryDate()) || StringUtils.isBlank(grossPayStr)|| StringUtils.isBlank(netPayrollStr)){
            paramterIllegal = false;
        }
        if(paramterIllegal){
            if(salaryService.findSalary(salaryMain.getEmpId(),salaryMain.getSalaryDate())!=null){
                br.setCode(ReponseCode.SALARY_EXIST_ERROR);
                return  br;
            }
            Double grossPayDouble = Double.parseDouble(grossPayStr) * ConverterSystem.MULTIPLE;
            Double netPayrollDouble = Double.parseDouble(netPayrollStr) * ConverterSystem.MULTIPLE;
            salaryMain.setGrossPay(grossPayDouble.longValue());
            salaryMain.setNetPayroll(netPayrollDouble.longValue());
            Map<String,String[]> map = request.getParameterMap();
            List<SalaryTypeEmp> list = new ArrayList<>();
            for(String key:map.keySet()){
                if(key.startsWith("salaryType_")){
                    if(map.get(key) != null && map.get(key).length != 0){
                        SalaryTypeEmp salaryTypeEmp = new SalaryTypeEmp();
                        String[] keys = key.split("_");
                        salaryTypeEmp.setSalaryType(Integer.parseInt(keys[1]));
                        Double amountDouble = Double.parseDouble(map.get(key)[0]) * ConverterSystem.MULTIPLE;
                        salaryTypeEmp.setMoney(amountDouble.longValue());
                        list.add(salaryTypeEmp);
                    }
                }
            }
            if(list.size()>0){
                salaryMain.setSalaryTypeEmpList(list);
                salaryMain.setCreateTime(new Date());
                salaryMain.setUpdateTime(salaryMain.getCreateTime());
                salaryService.addSalaryList(salaryMain);
                br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
            }else {
                br.setCode(ReponseCode.PARAMETER_NULL_ERROR);
            }
        }else {
            br.setCode(com.aitian.salary.Utils.ReponseCode.ILLEGAL_PARAMETER);
        }
        return br;
    }

    @RequestMapping(value = "/intoUpdate/*")
    public String intoUpdate(HttpServletRequest request) throws Exception {
        return "salarymanager/update";
    }
    @RequestMapping(value = "/intoShow/*")
    public String intoShow(HttpServletRequest request) throws Exception {
        return "salarymanager/show";
    }


    @RequestMapping(value = "/updateSalary", method = RequestMethod.PUT)
    @ResponseBody
    public BaseResponse updateSalary(HttpServletRequest request,  SalaryMain salaryMain) throws Exception {
        BaseResponse br = new BaseResponse();
        boolean paramterIllegal = true;
        String grossPayStr = request.getParameter("grossPay_dob");
        String netPayrollStr = request.getParameter("netPayroll_dob");
        if(StringUtils.isBlank(salaryMain.getEmpId()) || StringUtils.isBlank(salaryMain.getSalaryDate()) || StringUtils.isBlank(grossPayStr)|| StringUtils.isBlank(netPayrollStr) ||  salaryMain.getSalaryId()==null){
            paramterIllegal = false;
        }
        if(paramterIllegal){
            SalaryMain salaryMainOld = salaryService.findSalary(salaryMain.getEmpId(),salaryMain.getSalaryDate());

            Double grossPayDouble = Double.parseDouble(grossPayStr) * ConverterSystem.MULTIPLE;
            Double netPayrollDouble = Double.parseDouble(netPayrollStr) * ConverterSystem.MULTIPLE;
            salaryMain.setGrossPay(grossPayDouble.longValue());
            salaryMain.setNetPayroll(netPayrollDouble.longValue());
            Map<String,String[]> map = request.getParameterMap();
            List<SalaryTypeEmp> list = new ArrayList<>();
            for(String key:map.keySet()){
                if(key.startsWith("salaryType_")){
                    if(map.get(key) != null && map.get(key).length != 0){
                        SalaryTypeEmp salaryTypeEmp = new SalaryTypeEmp();
                        String[] keys = key.split("_");
                        salaryTypeEmp.setSalaryType(Integer.parseInt(keys[1]));
                        Double amountDouble = Double.parseDouble(map.get(key)[0]) * ConverterSystem.MULTIPLE;
                        salaryTypeEmp.setMoney(amountDouble.longValue());
                        list.add(salaryTypeEmp);
                    }
                }
            }
            if(list.size()>0){
                salaryMain.setSalaryTypeEmpList(list);
                salaryMain.setCreateTime(salaryMainOld.getCreateTime());
                salaryMain.setUpdateTime(salaryMain.getCreateTime());
//                salaryMain.setUpdateUserId(request.getSession().get);
                salaryService.updateSalarys(salaryMain);
                br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
            }else {
                br.setCode(ReponseCode.PARAMETER_NULL_ERROR);
            }
        }else {
            br.setCode(com.aitian.salary.Utils.ReponseCode.ILLEGAL_PARAMETER);
        }
        return br;
    }

    @RequestMapping(value = "/deleteSalary", method = RequestMethod.DELETE)
    @ResponseBody
    public BaseResponse deleteSalary(HttpServletRequest request, String salaryId) throws Exception {
        BaseResponse br = new BaseResponse();
        if(StringUtils.isNotBlank(salaryId)){
            salaryService.deleteSalary(Integer.parseInt(salaryId));
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else{
            br.setCode(com.aitian.salary.Utils.ReponseCode.PARAMETER_NULL_ERROR);
            br.setMessage("The parameter is NULL!");
        }
        return br;
    }

    @ResponseBody
    @RequestMapping(value = "/batchimport", method = RequestMethod.POST)
    public BaseResponse getParamFromFileForAjax(HttpServletRequest request) throws Exception  {
        BaseResponse br = new BaseResponse();
        br.setCode(ReponseCode.ILLEGAL_PARAMETER);
        String salaryDate = request.getParameter("salaryDate");
        if(!salaryDate.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]")){
            br.setCode(ReponseCode.PARAMETER_NULL_ERROR);
            return br;
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
        if(multiFileMap.size()>0){
            for (String key : multiFileMap.keySet()) {
                List<MultipartFile> multipartFiles = multiFileMap.get(key);
                if(multipartFiles.size()>0){
                    MultipartFile file = multipartFiles.get(0);
                    if (!file.isEmpty()) {
                        String fileName = file.getOriginalFilename();
                        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
                        if(!Arrays.asList(ConverterSystem.EXTENSIONPERMIT).contains(fileExtension)){
                            br.setCode(ReponseCode.NOT_ALLOW_FILE);
                            break;
                        }
                        String newfile = request.getSession().getServletContext().getRealPath("");
                        if(!new File(newfile+ File.separator+"upload").exists()){
                            new File(newfile+ File.separator+"upload").mkdirs();
                        }
                        File fileUpload = new File(newfile+ File.separator+"upload"+File.separator+file.getOriginalFilename());
                        FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(fileUpload));
                        int[] arr = salaryService.batchImport(new FileInputStream(fileUpload), file.getOriginalFilename(), salaryDate);
                        // arr 三个 第一个错误类型   第二、三个错误行、列数
                        if(arr[0] == 0){
                            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
                            br.setData(arr[1]);
                        }else {
                            br.setCode(ReponseCode.EXCEL_IMPORT_ERROR);
                            br.setMessage("The file import error!");
                            br.setData(arr);
                        }
                    }

                }else {
                    br.setCode(ReponseCode.HAS_NOT_FILE);
                }
                break;
            }
        }else{
            br.setCode(ReponseCode.HAS_NOT_FILE);
        }
        return br;
    }


    @RequestMapping(value = "/getDeparts", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getDepartIds(HttpServletRequest request) throws Exception {
        BaseResponse br = new BaseResponse();
        if(ConverterSystem.ALL_DEPARTMENT != null){
            br.setData(ConverterSystem.ALL_DEPARTMENT.values());
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else{
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_ERROR);
            br.setMessage("The DepartmentList is NULL!");
        }
        return br;
    }

    @RequestMapping(value = "/getEmpTypes", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getEmpTypes(HttpServletRequest request) throws Exception {
        BaseResponse br = new BaseResponse();
        if(ConverterSystem.ALL_EMPLOYEE_TYPE != null){
            br.setData(ConverterSystem.ALL_EMPLOYEE_TYPE.values());
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else{
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_ERROR);
            br.setMessage("The employee type List is NULL!");
        }
        return br;
    }

    @RequestMapping(value = "/getSalaryTypes", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getSalaryTypes(HttpServletRequest request, String empType) throws Exception {
        BaseResponse br = new BaseResponse();
        Map<String,Object> map = new HashMap<>();
        if(ConverterSystem.ALL_SALARY_TYPE != null){
            if(StringUtils.isNotBlank(empType)){
                if(empType.equals("0")){
                    map.put("formal",ConverterSystem.FORMAL_SALARY_TYPE);
                }else {
                    map.put("pact",ConverterSystem.PACT_SALARY_TYPE);
                }
            }else {
                map.put("formal",ConverterSystem.FORMAL_SALARY_TYPE);
                map.put("pact",ConverterSystem.PACT_SALARY_TYPE);
            }
            br.setData(map);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else{
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_ERROR);
            br.setMessage("The salary type List is NULL!");
        }
        return br;
    }

    @RequestMapping(value = "/getEmployees", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getEmployees(HttpServletRequest request, String empName, String departId) throws Exception {
        BaseResponse br = new BaseResponse();
        if(StringUtils.isNotBlank(departId)&&StringUtils.isNotBlank(empName)){
            List<Employee> emps = salaryService.getEmployees(empName.trim(), departId.trim());
            br.setData(emps);
            br.setCode(com.aitian.salary.Utils.ReponseCode.REQUEST_SUCCESS);
        }else{
            br.setCode(ReponseCode.PARAMETER_NULL_ERROR);
            br.setMessage("The PARAMETER_  is NULL!");
        }
        return br;
    }
}
