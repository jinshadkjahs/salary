package com.aitian.salary.service.impl;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.Utils.ReadExcel;
import com.aitian.salary.mapper.SalaryMapper;
import com.aitian.salary.model.SalaryMain;
import com.aitian.salary.model.SalaryType;
import com.aitian.salary.model.SalaryTypeEmp;
import com.aitian.salary.service.SalaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.FileInputStream;
import java.util.ArrayList;
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
        List<SalaryMain> salaryList = readExcel.getExcelInfo();

        if(salaryList != null){
            salaryMapper.insertList(salaryList);
        }

       return arr;
    }

    @Override
    public PageInfo<SalaryMain> findEmpSalary(String empName, String empId, String salaryDate, Integer departId, Integer empType, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
//        SalaryMain salaryMain = new SalaryMain();
//        salaryMain.setEmpId(empId);
//        if(empName != null && StringUtils.isNotBlank(empName)){
//            employeeSalary.setEmpName("%"+empName+"%");
//        }
//        employeeSalary.setDepartId(departId);
//        employeeSalary.setEmpType(empType);
//        List<EmployeeSalary> employeeSalaryList = salaryMapper.findEmp(employeeSalary);
//
//        employeeSalaryList.forEach(emp -> {
//            Example example = new Example(Salary.class);
//            Example.Criteria criteria = example.createCriteria();
//            if(StringUtils.isNotBlank(salaryDate)){
//                criteria.andBetween("salaryDate",salaryDate+"-01",salaryDate+"-31");
//            }
//            emp.setSalaryList(salaryMapper.selectByExample(example));
//            emp.setEmpTypeStr(ConverterSystem.ALL_EMPLOYEE_TYPE.get(emp.getEmpType()).getTypeName());
//            emp.setDepartIdStr(ConverterSystem.ALL_DEPARTMENT.get(emp.getDepartId()).getDepartName());
//            if(emp.getSalaryList().size()==0)
//            employeeSalaryList.remove(emp);
//        });
        PageInfo<SalaryMain> pageInfo = new PageInfo<>(new ArrayList<>());
        return  pageInfo;
    }

    @Transactional
    @Override
    public void addSalaryList(SalaryMain salaryMain) {
        salaryMapper.insert(salaryMain);
    }

    @Transactional
    @Override
    public void deleteSalary(String salaryId) {
        Example example = new Example(SalaryTypeEmp.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("salaryId",salaryId);
        salaryMapper.deleteByExample(example);
        salaryMapper.deleteByPrimaryKey(salaryId);
    }

    @Transactional
    @Override
    public void updateSalarys(SalaryMain salaryMain) {
        salaryMapper.updateByPrimaryKey(salaryMain);
    }
}
