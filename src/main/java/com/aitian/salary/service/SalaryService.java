package com.aitian.salary.service;


import com.aitian.salary.model.BonusInfo;
import com.aitian.salary.model.Employee;
import com.aitian.salary.model.SalaryMain;
import com.github.pagehelper.PageInfo;

import java.io.InputStream;
import java.util.List;

public interface SalaryService {

    int[] batchImport(InputStream inputStream, String fileName, String salaryDate);

    PageInfo<SalaryMain> findSalarys(String empName, String empId, String salaryDate, Integer departId, Integer empType, Integer page, Integer pageSize);

    void addSalaryList(SalaryMain salaryMain);

    void deleteSalary(Integer salaryId);

    void updateSalarys(SalaryMain salaryMain);

    SalaryMain findSalary(String empId, String salaryDate);

    List<Employee> getEmployees(String empName, String departId);

    List<BonusInfo> findBonusInfo(String empId);

    SalaryMain findSalaryByPk(Integer salaryId);
}
