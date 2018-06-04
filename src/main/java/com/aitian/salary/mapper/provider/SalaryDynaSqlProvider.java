package com.aitian.salary.mapper.provider;

import com.aitian.salary.model.Employee;
import com.aitian.salary.model.SalaryMain;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

public class SalaryDynaSqlProvider {
    public String findSalarys(final SalaryMain salaryMain){
        return new SQL(){
            {
                SELECT("*");
                FROM("s_employee s");
                FROM("s_salary_main a ");
                WHERE("s.empid = a.emp_id");
                Employee e = salaryMain.getEmployee();
                if(StringUtils.isNotBlank(e.getEmpType())){
                    WHERE("s.emp_type = #{employee.empType}");
                }
                if(StringUtils.isNotBlank(e.getEmpName())){
                    WHERE("s.emp_name like #{employee.empName}");
                }
                if(e.getDepartId()!=null){
                    WHERE("s.depart_id like #{employee.departId}");
                }
                if(StringUtils.isNotBlank(salaryMain.getSalaryDate())){
                    if(salaryMain.getSalaryDate().matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]")) {
                        WHERE("a.salary_date = #{salaryDate}");
                    }else {
                        WHERE("a.salary_date like #{salaryDate}");
                    }
                }
                if(StringUtils.isNotBlank(salaryMain.getEmpId())){
                    WHERE("s.empid = #{empId}");
                }
            }
        }.toString();
    }
}
