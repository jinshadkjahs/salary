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
        employee.setEmpId(empId);
        List<Employee> employees = employeeMapper.queryEmployees(employee);

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
        int modifyNums = 0;
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
        short firstCellNum = 0;
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
                    firstCellNum = row.getFirstCellNum();
                    Object cellValue = null;
                    cell = row.getCell(y);
                    cellValue = readExcel.getCellValue(cell);
                    if(cellValue instanceof Double  ){
                        cellValue = String.valueOf(cellValue);
                    }
                    if(cellValue == null){
                        cellValue="";
                    }
                    li.add((String) cellValue);
                }
                list.add(li);
            }
        }
        if(list != null){
            //员工数据导入
                for(int i=0; i<list.size(); i++){
                    List<String> list1 = list.get(i);
                    String empId="";
                    String empDepartment="";
                    Integer empDepId=0;
                    String empName="";
                    String baseSalary="";
                    String empPhone="";
                    String empCardNum="";
                    String empType="";
                    if(list1.size()==8){
                        empId = list1.get(1);
                        //员工部门
                        empDepartment = list1.get(2);
                        //部门ID
                        empDepId = null;
                        //员工姓名
                        empName = list1.get(3);
                        //基本工资
                        baseSalary = list1.get(4);
                        //手机号
                        empPhone = list1.get(5);
                        //身份证编码
                        empCardNum = list1.get(6);
                        //员工类型
                        empType = list1.get(7);
                        //用decimal
                        //final float fBaseSalary = Float.parseFloat(baseSalary);
                    }else if(list1.size()==7){
                        empId = list1.get(0);
                        //员工部门
                        empDepartment = list1.get(1);
                        //部门ID
                        empDepId = null;
                        //员工姓名
                        empName = list1.get(2);
                        //基本工资
                        baseSalary = list1.get(3);
                        //手机号
                        empPhone = list1.get(4);
                        //身份证编码
                        empCardNum = list1.get(5);
                        //员工类型
                        empType = list1.get(6);
                        //用decimal
                        //final float fBaseSalary = Float.parseFloat(baseSalary);
                    }

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
                    //若不存在插入数据
                    if(list1.size()==0){
                        try{
                            this.employeeMapper.insertEmp(employee);
                            this.employeeMapper.insertUser(user);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                        //若存在该员工判断是否和存在的数据相同，若相同不执行操作,若不相同修改该员工
                    }else{
                        Employee employee1 = list1.get(0);
                        if(judgmentEmployee(employee1,employee)){
                            failNums++;
                            continue;
                        }else {
                            this.employeeMapper.modifyEmployee(employee);
                            failEmpNo+=employee.getEmpId()+" ";
                            modifyNums++;
                        }
                    }
                }
                successNums = importNums-failNums-modifyNums;
                importEmpInfo.setSuccessNums(successNums);
                importEmpInfo.setFailNums(modifyNums);
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

    private static boolean judgmentEmployee(Employee employee1,Employee employee){
        boolean isEqual = false;

        String empName = employee1.getEmpName()==null?"":employee1.getEmpName();
        String empName1 = employee.getEmpName()==null?"":employee.getEmpName();
        String empId = employee1.getEmpId()==null?"":employee1.getEmpId();
        String empId1 = employee.getEmpId()==null?"":employee.getEmpId();
        Integer departId = employee1.getDepartId()==null?0:employee1.getDepartId();
        Integer departId1 = employee.getDepartId()==null?0:employee.getDepartId();
        Integer baseSalary = employee1.getBaseSalary()==null?0:employee1.getBaseSalary();
        Integer baseSalary1 = employee.getBaseSalary()==null?0:employee.getBaseSalary();
        String empPhone = employee1.getEmpPhone()==null?"":employee1.getEmpPhone();
        String empPhone1 = employee.getEmpPhone()==null?"":employee.getEmpPhone();
        String empCardNum = employee1.getEmpCardNum()==null?"":employee1.getEmpCardNum();
        String empCardNum1 = employee.getEmpCardNum()==null?"":employee.getEmpCardNum();
        String empType = employee1.getEmpType()==null?"":employee1.getEmpType();
        String empType1 = employee.getEmpType()==null?"":employee.getEmpType();

        if(empCardNum.equals(empCardNum1)
                &&empId.equals(empId1)
                &&empName.equals(empName1)
                &&empPhone.equals(empPhone1)
                &&empType.equals(empType1)
                &&departId.equals(departId1)
                &&baseSalary.equals(baseSalary1)){

            isEqual = true;
        }/*
        if(empCardNum.equals(empCardNum1)){
            System.out.println("empCardNum");
        }if(empId.equals(empId1)){
            System.out.println("empId");
        }if(employee.equals(employee1)){
            System.out.println("empCardNum");
        }if(empName.equals(empName1)){
            System.out.println("empCardNum");
        }if(empPhone.equals(empPhone1)){
            System.out.println("empCardNum");
        }if(departId.equals(departId1)){
            System.out.println("empCardNum");
        }if(baseSalary.equals(baseSalary1)){
            System.out.println("empCardNum");
        }*/
        return isEqual;

    }


}

