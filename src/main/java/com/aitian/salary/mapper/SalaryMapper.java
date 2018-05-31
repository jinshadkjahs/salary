package com.aitian.salary.mapper;

import com.aitian.salary.Utils.BaseMapper;
import com.aitian.salary.mapper.provider.SalaryDynaSqlProvider;
import com.aitian.salary.model.EmployeeSalary;
import com.aitian.salary.model.Salary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SalaryMapper extends BaseMapper<Salary> {

    @SelectProvider(type=SalaryDynaSqlProvider.class, method = "findSalary")
    List<Salary> findSalary(Salary salary);

    @SelectProvider(type=SalaryDynaSqlProvider.class, method = "findEmp")
    List<Salary> findEmp(EmployeeSalary emp);

}
