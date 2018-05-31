package com.aitian.salary.service.impl;

import com.aitian.salary.Utils.ReadExcel;
import com.aitian.salary.mapper.SalaryMapper;
import com.aitian.salary.model.EmployeeSalary;
import com.aitian.salary.model.Salary;
import com.aitian.salary.service.SalaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.FileInputStream;
import java.util.List;

@Service(value = "salaryService")
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryMapper salaryMapper;

    @Transactional
    @Override
    public int[] batchImport( FileInputStream inputStream, String fileName) {

        //创建处理EXCEL
        ReadExcel readExcel=new ReadExcel();
        //解析excel，获取信息集合。
        int[] arr = readExcel.checkExcel(inputStream, fileName);
        List<Salary> salaryList = readExcel.getExcelInfo();

        if(salaryList != null){
            salaryMapper.insertList(salaryList);
        }

       return arr;
    }

    @Override
    public PageInfo<EmployeeSalary> findEmpSalary(String empName, String empId, String salaryDate, Integer departId, Integer empType, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        EmployeeSalary employeeSalary = new EmployeeSalary();
        employeeSalary.setEmpId(empId);
        employeeSalary.setEmpName(empName);
        employeeSalary.setDepartId(departId);
        employeeSalary.setEmpType(empType);
        List<EmployeeSalary> employeeSalaryList = salaryMapper.findEmp(employeeSalary);

        Salary salary = new Salary();
        salary.setSalaryDate(salaryDate);
        employeeSalaryList.forEach(emp -> {
            salary.setEmpId(emp.getEmpId());
            emp.setSalaryList(salaryMapper.select(salary));
        });
        PageInfo<EmployeeSalary> pageInfo = new PageInfo<>(employeeSalaryList);
        return  pageInfo;
    }

    @Transactional
    @Override
    public void addSalaryList(List<Salary> salaryList) {
        salaryMapper.insertList(salaryList);
    }

    @Override
    public void deleteSalaryByEmpId(String empId) {
        Example example = new Example(Salary.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("empId",empId);
        salaryMapper.deleteByExample(example);
    }

    @Transactional
    @Override
    public void updateSalarys(List<Salary> salaryList) {
        if(salaryList.size()>0){
            Example example = new Example(Salary.class);
            Example.Criteria criteria = example.createCriteria();
            String empId = salaryList.get(0).getEmpId();
            criteria.andEqualTo("empId",empId);
            salaryMapper.deleteByExample(example);
            salaryMapper.insertList(salaryList);
        }
    }
}
