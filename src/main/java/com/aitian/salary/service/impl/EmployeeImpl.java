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
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
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
    public XSSFWorkbook exportEmpModel(String exportType) {
        XSSFWorkbook  workBook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workBook.createCellStyle();
        XSSFDataFormat format = workBook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        String[] rowName = null;
        String[] empTypes = new String[]{"正式员工","合同员工"};
        //统一模板
        if(EmpConstant.NON_OFFICIAL_EMPLOYEE.equals(exportType)){
            rowName = new String[]{"序号","员工编号","科室","姓名","工资","手机号","身份证编号","员工类型"};
        }
        /*//正式员工
        if(EmpConstant.OFFICIAL_EMPLOYEE.equals(exportType)){
            rowName = new String[]{"单位","姓名","职称","编号","工作时间","月工资","挂率","回民","保健","夜班",
                    "护岗","岗位","工种","等级","岗级","房补","卫生费","公积金","GJJ","电视费","十五","养老","医疗",
                    "失业","月缴费工资","ASD","见习工资","医保号","养老号","SFZH","SY","ZH1","养老1","失业1"};
        }*/
        XSSFSheet sheet = workBook.createSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(1, 65535, 7, 7);
        DataValidationConstraint constraint = helper.createExplicitListConstraint(empTypes);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        //处理Excel兼容性问题
        if(dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
        workBook.setSheetName(0,"员工导入模板");
        //所有列都设置成文本
        for(int i=0;i<rowName.length;i++){
            sheet.setColumnWidth((short)i,(short)3000);
            sheet.setDefaultColumnStyle(i, cellStyle);
        }
        //列名
        XSSFRow headRow = sheet.createRow(0);
        int columnNum = rowName.length;
        for(int i=0; i<columnNum; i++){
            headRow.createCell(i).setCellValue(rowName[i]);
        }

    return workBook;
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
            //员工数据导入
                for(int i=0; i<list.size(); i++){
                    List<String> list1 = list.get(i);
                    if(list1.size()<8){
                        continue;
                    }
                    String empId = list1.get(1);
                    //员工部门
                    String empDepartment = list1.get(2);
                    //部门ID
                    Integer empDepId = null;
                    //员工姓名
                    String empName = list1.get(3);
                    //基本工资
                    String baseSalary = list1.get(4);
                    //手机号
                    String empPhone = list1.get(5);
                    //身份证编码
                    String empCardNum = list1.get(6);
                    //员工类型
                    String empType = list1.get(7);
                    //用decimal
                    //final float fBaseSalary = Float.parseFloat(baseSalary);
                    if(StringUtils.isEmpty(empId)){
                        empId = "";
                    }
                    if(StringUtils.isEmpty(empName)){
                        empName = "";
                    }
                    if(StringUtils.isEmpty(baseSalary)){
                        baseSalary = "0.00";
                    }
                    if(StringUtils.isEmpty(empPhone)){
                        empPhone="";
                    }
                    if(StringUtils.isEmpty(empCardNum)){
                        empCardNum="";
                    }
                    if(StringUtils.isEmpty(empType)){
                        empType="";
                    }
                    if(EmpConstant.STR_OFFICIAL_EMPLOYEE.equals(empType)){
                        empType=EmpConstant.OFFICIAL_EMPLOYEE;
                    }
                    if(EmpConstant.STR_NON_OFFICIAL_EMPLOYEE.equals(empType)){
                        empType=EmpConstant.NON_OFFICIAL_EMPLOYEE;
                    }
                    //departName
                    Collection<Department> values = ConverterSystem.ALL_DEPARTMENT.values();
                    Iterator<Department> iterator = values.iterator();
                    while (iterator.hasNext()){
                        Department next = iterator.next();
                        final String departName = next.getDepartName();
                        if(empDepartment.equals(departName)){
                            //部门ID
                            empDepId =next.getDepartid();
                        }
                    }
                    final float fBaseSalary = Float.parseFloat(baseSalary);
                    Employee employee = new Employee();
                    employee.setEmpId(empId);
                    employee.setDepartId(empDepId);
                    employee.setEmpName(empName);
                    employee.setBaseSalary((int) fBaseSalary);
                    employee.setEmpType(empType);
                    employee.setEmpPhone(empPhone);
                    employee.setEmpCardNum(empCardNum);
                    listsingleEmp.add(employee);
                    empList.addAll(listsingleEmp);

                    User user = new User();
                    user.setEmpId(empId);
                    user.setPassword(EmpConstant.DEFAULT_PASSWORD);
                    user.setUserType(EmpConstant.COMMON_USER);
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
                    //员工存在，修改员工信息
                        this.employeeMapper.modifyEmployee(employee);
                        //作为修改员工数
                        failNums++;
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

