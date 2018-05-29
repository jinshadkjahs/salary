package com.aitian.salary.mapper;

import com.aitian.salary.model.Department;
import com.aitian.salary.model.EmployeeType;
import com.aitian.salary.model.SalaryType;
import com.aitian.salary.model.UserType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoadingMapper {

    @Select("select * from s_user_type")
    public List<UserType> selectAllUserType();

    @Select("select * from s_emp_type")
    public List<EmployeeType> selectAllEmployeeType();

    @Select("select * from s_salary_type")
    public List<SalaryType> selectAllSalaryType();

    @Select("select * from s_department")
    public List<Department> selectAllDepartment();
}
