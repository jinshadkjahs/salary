package com.aitian.salary.mapper.provider;

import com.aitian.salary.model.Employee;
import com.aitian.salary.model.User;
import com.sun.org.apache.bcel.internal.generic.IFNULL;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author yisicheng 2018-05-29
 * 方法尚未补全 需后续补全
 */
public class EmployeeDynaSqlProvider {
    //动态拼sql

    public String queryEmpAndUser(final Employee employee){
        String s_employee_s = new SQL() {
            {
                SELECT("*");
                FROM("s_employee s");
                JOIN("s_user e on s.empid = e.empid");
                if (StringUtils.isNotBlank(employee.getEmpId())) {
                    WHERE("s.empid = #{empId}");
                }
            }
        }.toString();
        return s_employee_s;
    }

    public String queryEmployees(final Employee employee){
        String s_employee_s = new SQL() {
            {
                SELECT("*");
                FROM("s_employee s");
                JOIN("s_user e on s.empid = e.empid");
                if (StringUtils.isNotBlank(employee.getEmpId())) {
                    WHERE("s.empid = #{empId}");
                }
            }
        }.toString();
        System.out.println(s_employee_s);
        return s_employee_s;
    }

    public String insertEmp(final Employee employee){

        return new SQL(){
            {
                INSERT_INTO("s_employee");
                VALUES("empid","#{empId}");
                VALUES("emp_name","#{empName}");
                VALUES("emp_type","#{empType}");
                VALUES("waltz_date","now()");
                VALUES("create_time","now()");
                VALUES("update_time","now()");
                VALUES("emp_phone","#{empPhone}");
                VALUES("emp_card_num","#{empCardNum}");
                VALUES("depart_id","#{departId}");
                VALUES("base_salary","#{baseSalary}");
            }
        }.toString();
    }

    public String insertUser(User user){
        return new SQL(){
            {
              INSERT_INTO("s_user");
              VALUES("empid","#{empId}");
              VALUES("password","#{password}");
              VALUES("create_time","now()");
              VALUES("update_time","now()");
              VALUES("user_type","#{userType}");
            }
        }.toString();
    }

    public String modifyEmployee(Employee employee){
        return new SQL(){
            {
                UPDATE("s_employee");
                SET("emp_name=#{empName}","emp_type=#{empType}","update_time=now()",
                        "emp_phone=#{empPhone}","emp_card_num=#{empCardNum}","depart_id=#{departId}","base_salary=#{baseSalary}");
                WHERE("empid=#{empId}");
            }

        }.toString();
    }

}
