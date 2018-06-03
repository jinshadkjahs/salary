package com.aitian.salary.mapper;

import com.aitian.salary.Utils.BaseMapper;
import com.aitian.salary.mapper.provider.EmployeeDynaSqlProvider;
import com.aitian.salary.model.Employee;
import org.apache.ibatis.annotations.Mapper;
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

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//    public List<Map<String,Object>> findEmployee(String empId,String empName){
//        StringBuffer sb = new StringBuffer();
//        sb.append(" SELECT * FROM s_employee emp").append(" where 1=1");
//                if(!StringUtils.isEmpty(empId)){
//                    sb.append(" and emp.empid='"+empId+"'");
//                }
//                if(!StringUtils.isEmpty(empName)){
//                    sb.append(" and emp.emp_name='"+empName+"'");
//                }
//        logger.info(sb.toString());
//        return this.jdbcTemplate.queryForList(sb.toString());
//    }

 /*   public List<Employee> insertEmployee();

    public List<Employee> updateEmployee();

    public List<Employee> delEmployee();*/

}
