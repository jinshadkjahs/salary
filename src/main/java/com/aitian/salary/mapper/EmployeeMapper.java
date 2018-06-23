package com.aitian.salary.mapper;

import com.aitian.salary.Utils.BaseMapper;
import com.aitian.salary.mapper.provider.EmployeeDynaSqlProvider;
import com.aitian.salary.model.Employee;
import com.aitian.salary.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
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

    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "insertEmp")
    void insertEmp(Employee emp);

    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "insertUser")
    void insertUser(User user);

    @SelectProvider(type = EmployeeDynaSqlProvider.class,method = "modifyEmployee")
    void modifyEmployee(Employee employee);


}
