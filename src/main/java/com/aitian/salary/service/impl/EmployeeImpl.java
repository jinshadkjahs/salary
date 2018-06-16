package com.aitian.salary.service.impl;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.Utils.EmpConstant;
import com.aitian.salary.Utils.ReadExcel;
import com.aitian.salary.mapper.EmployeeMapper;
import com.aitian.salary.mapper.UserMapper;
import com.aitian.salary.model.Department;
import com.aitian.salary.model.Employee;
import com.aitian.salary.model.ImportEmpInfo;
import com.aitian.salary.model.User;
import com.aitian.salary.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service(value = "employeeService")
public class EmployeeImpl implements EmployeeService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<Employee> findEmployee(String empId, String empName,String empType, Integer page, Integer pageSize){
        PageHelper.startPage(page, pageSize);
        Employee employee = new Employee();
        if(StringUtils.isNotBlank(empId)){
            employee.setEmpId(empId);
        }
        if(StringUtils.isNotBlank(empName)){
            employee.setEmpName(empName);
        }
        if(StringUtils.isNotBlank(empType)){
            employee.setEmpType(empType);
        }

        List<Employee> employeeList = employeeMapper.select(employee);
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        return pageInfo;
    }

    @Override
    public Employee queryEmpForUser(String empId) {
        Employee employee = new Employee();
        if(StringUtils.isNotBlank(empId)){
            employee.setEmpId(empId);
        }
        Employee emp = employeeMapper.selectByPrimaryKey(employee);
        return emp;
    }

    @Override
    public List<Employee> getEmp(String empId) {
        Employee employee = new Employee();
        if(StringUtils.isNotBlank(empId)){
            employee.setEmpId(empId);
        }
        List<Employee> employees = employeeMapper.select(employee);
        return employees;
    }

    @Override
    public void modifyEmp(Employee employee) {
        this.employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public void deleteEmp(String empId) {
        Employee employee = new Employee();
        User user = new User();
        if(StringUtils.isNotBlank(empId)){
            employee.setEmpId(empId);
            user.setEmpId(empId);
        }
        employeeMapper.delete(employee);
        userMapper.delete(user);
    }

    @Override
    public List<Employee> queryEmpAndUser(String empId) {
        Employee employee = new Employee();
        if(StringUtils.isNotBlank(empId)){
            employee.setEmpId(empId);
        }
        List<Employee> employees = employeeMapper.queryEmpAndUser(employee);

        return employees;
    }

    @Override
    public ImportEmpInfo importEmp(InputStream in, String fileName) throws Exception {

        List<Employee> empList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<Employee> listsingleEmp = new ArrayList<>();
        List<User> listsingleUser = new ArrayList<>();
        ReadExcel readExcel = new ReadExcel();
        List<List<String>> list = null;
        int successNums = 0;
        int failNums = 0;
        int importNums = 0;
        String failEmpNo="";
        ImportEmpInfo importEmpInfo = new ImportEmpInfo();
        Workbook work = readExcel.getWorkbook(in,fileName);
        if(null == work){
            throw new Exception("创建Excel工作薄为空！");
        }

        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        int columnNum=0;
        list = new ArrayList<>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if(sheet==null){continue;}

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum()+1; j < sheet.getLastRowNum()+1; j++) {
                row = sheet.getRow(j);
                if(row==null){continue;}
                //  if(row==null||row.getFirstCellNum()==j){continue;}

                //遍历所有的列
                List<String> li = new ArrayList<>();
                columnNum = row.getPhysicalNumberOfCells();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add((String) readExcel.getCellValue(cell));
                }
                list.add(li);
            }
        }
        if(list != null){
            //合同工导入数据
            if(columnNum== EmpConstant.NON_OFFICIAL_EMPLOYEE_NUMS){
                for(int i=0; i<list.size(); i++){
                    List<String> list1 = list.get(i);
                    //员工编号
                    String empId = list1.get(1);
                    //员工部门
                    String empDepartment = list1.get(2);
                    //部门ID
                    Integer empDepId = null;
                    //员工姓名
                    String empName = list1.get(3);
                    //基本工资
                    String baseSalary = list1.get(4);
                    //用decimal
                    //final float fBaseSalary = Float.parseFloat(baseSalary);
                    if(StringUtils.isEmpty(empId)){
                        empId = "unknow";
                    }
                    if(StringUtils.isEmpty(empName)){
                        empName = "unknow";
                    }

                    if(StringUtils.isEmpty(baseSalary)){
                        baseSalary = "0.00";
                    }
                    //departName
                    Collection<Department> values = ConverterSystem.ALL_DEPARTMENT.values();
                    Iterator<Department> iterator = values.iterator();
                    while (iterator.hasNext()){
                        Department next = iterator.next();
                        final String departName = next.getDepartName();
                        if(empDepartment.equals(departName)){
                            empDepId =next.getDepartid();
                        }
                    }
                    final float fBaseSalary = Float.parseFloat(baseSalary);
                    Employee employee = new Employee();
                    employee.setEmpId(empId);
                    employee.setDepartId(empDepId);
                    employee.setEmpName(empName);
                    employee.setBaseSalary((int) fBaseSalary);
                    employee.setEmpType(EmpConstant.NON_OFFICIAL_EMPLOYEE);
                    listsingleEmp.add(employee);
                    empList.addAll(listsingleEmp);

                    User user = new User();
                    user.setEmpId(empId);
                    user.setPassword(EmpConstant.DEFAULT_PASSWORD);
                    user.setUserType(EmpConstant.NON_OFFICIAL_EMPLOYEE);
                    listsingleUser.add(user);
                    userList.addAll(listsingleUser);
                    if(listsingleEmp!=null && listsingleUser!=null){
                        listsingleEmp.clear();
                        listsingleUser.clear();
                    }
                }
                importNums = empList.size();
                for(int i =0; i<empList.size(); i++){
                    final Employee employee = empList.get(i);
                    final User user = userList.get(i);
                    List<Employee> list1 = this.queryEmpAndUser(employee.getEmpId());
                    if(list1.size()>0){
                        failNums++;
                        if(i<empList.size()){
                            failEmpNo+=employee.getEmpId()+",";
                        }else{
                            failEmpNo+=employee.getEmpId();
                        }
                        continue;
                    }
                    try{
                       this.employeeMapper.insertEmp(employee);
                       this.employeeMapper.insertUser(user);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
                successNums = importNums-failNums;
                importEmpInfo.setSuccessNums(successNums);
                importEmpInfo.setFailNums(failNums);
                importEmpInfo.setFailEmpNo(failEmpNo);
                if(successNums==importNums){
                    importEmpInfo.setAllImport(true);
                }else{
                    importEmpInfo.setAllImport(false);
                }

            }
            //正式工数据导入
            if(columnNum== EmpConstant.OFFICIAL_EMPLOYEE_NUMS){
                for(int i=0; i<list.size(); i++){
                    List<String> list1 = list.get(i);
                    //员工编号
                    String empId = list1.get(27);
                    //员工部门
                    String empDepartment = list1.get(0);
                    //部门ID
                    Integer empDepId = null;
                    //员工姓名
                    String empName = list1.get(1);
                    //基本工资
                    String baseSalary = list1.get(5);
                    //用decimal
                    //final float fBaseSalary = Float.parseFloat(baseSalary);
                    if(StringUtils.isEmpty(empId)){
                        empId = "unknow";
                    }
                    if(StringUtils.isEmpty(empName)){
                        empName = "unknow";
                    }

                    if(StringUtils.isEmpty(baseSalary)){
                        baseSalary = "0.00";
                    }
                    //departName
                    Collection<Department> values = ConverterSystem.ALL_DEPARTMENT.values();
                    Iterator<Department> iterator = values.iterator();
                    while (iterator.hasNext()){
                        Department next = iterator.next();
                        final String departName = next.getDepartName();
                        if(empDepartment.equals(departName)){
                            empDepId =next.getDepartid();
                        }
                    }
                    final float fBaseSalary = Float.parseFloat(baseSalary);
                    Employee employee = new Employee();
                    employee.setEmpId(empId);
                    employee.setDepartId(empDepId);
                    employee.setEmpName(empName);
                    employee.setBaseSalary((int) fBaseSalary);
                    employee.setEmpType(EmpConstant.OFFICIAL_EMPLOYEE);
                    listsingleEmp.add(employee);
                    empList.addAll(listsingleEmp);

                    User user = new User();
                    user.setEmpId(empId);
                    user.setPassword(EmpConstant.DEFAULT_PASSWORD);
                    user.setUserType(EmpConstant.OFFICIAL_EMPLOYEE);
                    listsingleUser.add(user);
                    userList.addAll(listsingleUser);
                    if(listsingleEmp!=null && listsingleUser!=null){
                        listsingleEmp.clear();
                        listsingleUser.clear();
                    }
                }
                importNums = empList.size();
                for(int i =0; i<empList.size(); i++){
                    final Employee employee = empList.get(i);
                    final User user = userList.get(i);
                    List<Employee> list1 = this.queryEmpAndUser(employee.getEmpId());
                    if(list1.size()>0){
                        failNums++;
                        if(i<empList.size()){
                            failEmpNo+=employee.getEmpId()+",";
                        }else{
                            failEmpNo+=employee.getEmpId();
                        }
                        continue;
                    }
                    try{
                        this.employeeMapper.insertEmp(employee);
                        this.employeeMapper.insertUser(user);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            }
            successNums = importNums-failNums;
            importEmpInfo.setSuccessNums(successNums);
            importEmpInfo.setFailNums(failNums);
            importEmpInfo.setFailEmpNo(failEmpNo);
            if(successNums==importNums){
                importEmpInfo.setAllImport(true);
            }else{
                importEmpInfo.setAllImport(false);
            }
        }
        logger.info("员工导入完成！"+importEmpInfo.toString());
        return importEmpInfo;
    }


}

