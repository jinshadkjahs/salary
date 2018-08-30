package com.aitian.salary.mapper;

import com.aitian.salary.Utils.BaseMapper;
import com.aitian.salary.mapper.provider.EmployeeDynaSqlProvider;
import com.aitian.salary.model.Employee;
import com.aitian.salary.model.User;
import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "queryEmpAndUser")
    List<Employee> queryEmpAndUser(Employee emp);

    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "queryEmployees")
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
            @Result(property="baseSalary",column="base_salary")
    })
    List<Employee> queryEmployees(Employee employee);

    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "insertEmp")
    void insertEmp(Employee emp);

    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "insertUser")
    void insertUser(User user);

    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "modifyEmployee")
    void modifyEmployee(Employee employee);


}
