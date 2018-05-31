package com.aitian.salary.mapper.provider;

import com.aitian.salary.model.EmployeeSalary;
import com.aitian.salary.model.Salary;
import com.aitian.salary.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author yisicheng 2018-05-30
 */
public class SalaryDynaSqlProvider {
    //动态拼sql

    public String findSalary(final Salary salary) {
        return new SQL() {
            {
                SELECT("*");
                FROM("s_salary s");
                LEFT_OUTER_JOIN("s_salary_type t on s.salary_type = t.salary_type");
                if(StringUtils.isNotBlank(salary.getEmpId())){
                    WHERE("s.emp_id = #{empId}");
                }
                if(StringUtils.isNotBlank(salary.getSalaryDate())){
                    WHERE("s.salary_date = #{salaryDate}");
                }
            }
        }.toString();
    }

    public String findEmp(final EmployeeSalary emp) {
        return new SQL() {
            {
                SELECT("*");
                FROM("s_employee s");
                if(StringUtils.isNotBlank(emp.getEmpId())){
                    WHERE("s.empid = #{empId}");
                }
                if(emp.getEmpType() != null){
                    WHERE("s.emp_type = #{empType}");
                }
                if(emp.getDepartId() != null){
                    WHERE("s.depart_id = #{departId}");
                }
                if(StringUtils.isNotBlank(emp.getEmpName())){
                    WHERE("s.emp_name like #{empName}");
                }
            }
        }.toString();
    }

}
