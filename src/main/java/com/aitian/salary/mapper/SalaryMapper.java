package com.aitian.salary.mapper;

import com.aitian.salary.Utils.BaseMapper;
import com.aitian.salary.mapper.provider.SalaryDynaSqlProvider;
import com.aitian.salary.model.EmployeeSalary;
import com.aitian.salary.model.Salary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SalaryMapper extends BaseMapper<Salary> {

    @SelectProvider(type=SalaryDynaSqlProvider.class, method = "findSalary")
    List<Salary> findSalary(Salary salary);

    @SelectProvider(type=SalaryDynaSqlProvider.class, method = "findEmp")
    @Results({
            @Result(id=true,property="empId",column="empid"),
            @Result(property="empName",column="emp_name"),
            @Result(property="empPhone",column="emp_phone"),
            @Result(property="empCardNum",column="emp_card_num"),
            @Result(property="empType",column="emp_type"),
            @Result(property="waltzDate",column="waltz_date"),
            @Result(property="departId",column="depart_id"),
            @Result(property="createTime",column="create_time"),
            @Result(property="updateTime",column="update_time"),
            @Result(property="baseSalary",column="base_salary"),
    })
    List<EmployeeSalary> findEmp(EmployeeSalary emp);

}
