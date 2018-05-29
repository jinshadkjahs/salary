package com.aitian.salary.service;

import com.aitian.salary.model.Department;
import com.aitian.salary.model.EmployeeType;
import com.aitian.salary.model.SalaryType;
import com.aitian.salary.model.UserType;

import java.util.List;

public interface LoadingService {

    public List<UserType> selectAllUserType();

    public List<EmployeeType> selectAllEmployeeType();

    public List<SalaryType> selectAllSalaryType();

    public List<Department> selectAllDepartment();
}
