package com.aitian.salary.service;

import com.aitian.salary.model.EmployeeSalary;
import com.aitian.salary.model.Salary;
import com.github.pagehelper.PageInfo;

import java.io.FileInputStream;
import java.util.List;

public interface SalaryService {

    int[] batchImport( FileInputStream inputStream, String fileName);

    PageInfo<EmployeeSalary> findEmpSalary(String empName, String empId, String salaryDate, Integer departId, Integer empType, Integer page, Integer pageSize);

    void addSalaryList(List<Salary> salaryList);

    void deleteSalaryByEmpId(String empId);

    void updateSalarys(List<Salary> salaryList);
}
