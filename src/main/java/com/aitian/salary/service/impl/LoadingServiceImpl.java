package com.aitian.salary.service.impl;

import com.aitian.salary.mapper.LoadingMapper;
import com.aitian.salary.model.Department;
import com.aitian.salary.model.EmployeeType;
import com.aitian.salary.model.SalaryType;
import com.aitian.salary.model.UserType;
import com.aitian.salary.service.LoadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "loadingService")
public class LoadingServiceImpl implements LoadingService {

    @Autowired
    private LoadingMapper loadingMapper;

    @Override
    public List<UserType> selectAllUserType() {
        return loadingMapper.selectAllUserType();
    }

    @Override
    public List<EmployeeType> selectAllEmployeeType() {
        return loadingMapper.selectAllEmployeeType();
    }

    @Override
    public List<SalaryType> selectAllSalaryType() {
        return loadingMapper.selectAllSalaryType();
    }

    @Override
    public List<Department> selectAllDepartment() {
        return loadingMapper.selectAllDepartment();
    }
}
