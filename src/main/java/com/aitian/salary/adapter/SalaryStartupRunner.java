package com.aitian.salary.adapter;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.model.Department;
import com.aitian.salary.model.EmployeeType;
import com.aitian.salary.model.SalaryType;
import com.aitian.salary.model.UserType;
import com.aitian.salary.service.LoadingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务启动执行
 * @Author yisicheng 2018-05-29
 */
@Component
public class SalaryStartupRunner implements CommandLineRunner {

    @Resource
    private LoadingService loadingService;

    @Override
    public void run(String... args) throws Exception {

        /**
         * 加载所有科室信息
         */
        List<Department> departmentList = loadingService.selectAllDepartment();
        ConverterSystem.ALL_DEPARTMENT = departmentList.stream().collect(Collectors.toMap(Department::getDepartid, department -> department));
        /**
         * 加载所有员工类型信息
         */
        List<EmployeeType> employeeTypes = loadingService.selectAllEmployeeType();
        ConverterSystem.ALL_EMPLOYEE_TYPE = employeeTypes.stream().collect(Collectors.toMap(EmployeeType::getEmpType, employeeType -> employeeType));

        /**
         * 加载所有工资类型信息
         */
        List<SalaryType> salaryTypes = loadingService.selectAllSalaryType();
        ConverterSystem.ALL_SALARY_TYPE = salaryTypes.stream().collect(Collectors.toMap(SalaryType::getSalaryType, salaryType -> salaryType));

        /**
         * 加载所有用户类型
         */
        List<UserType> userTypes = loadingService.selectAllUserType();
        ConverterSystem.ALL_USER_TYPE = userTypes.stream().collect(Collectors.toMap(UserType::getUserType, userType -> userType));

    }

}