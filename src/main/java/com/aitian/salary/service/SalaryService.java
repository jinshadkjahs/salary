package com.aitian.salary.service;


import com.aitian.salary.model.Employee;
import com.aitian.salary.model.SalaryMain;
import com.github.pagehelper.PageInfo;

import java.io.FileInputStream;
import java.util.List;

public interface SalaryService {

    int[] batchImport( FileInputStream inputStream, String fileName);

    PageInfo<SalaryMain> findSalarys(String empName, String empId, String salaryDate, Integer departId, Integer empType, Integer page, Integer pageSize);

    void addSalaryList(SalaryMain salaryMain);

    void deleteSalary(Integer salaryId);

    void updateSalarys(SalaryMain salaryMain);

    SalaryMain findSalary(String empId, String salaryDate);

    List<Employee> getEmployees(String empName, String departId);
}
