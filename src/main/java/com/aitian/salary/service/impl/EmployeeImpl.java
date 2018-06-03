package com.aitian.salary.service.impl;

import com.aitian.salary.mapper.EmployeeMapper;
import com.aitian.salary.model.Employee;
import com.aitian.salary.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "employeeService")
public class EmployeeImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public PageInfo<Employee> findEmployee(String empId, String empName, Integer page, Integer pageSize){
        PageHelper.startPage(page, pageSize);
        Employee employee = new Employee();
        if(StringUtils.isNotBlank(empId)){
            employee.setEmpId(empId);
        }
        if(StringUtils.isNotBlank(empName)){
            employee.setEmpName(empName);
        }
        List<Employee> employeeList = employeeMapper.selectAll();
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
}

