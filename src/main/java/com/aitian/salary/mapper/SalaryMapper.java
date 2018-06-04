package com.aitian.salary.mapper;

import com.aitian.salary.Utils.BaseMapper;
import com.aitian.salary.mapper.provider.SalaryDynaSqlProvider;
import com.aitian.salary.model.SalaryMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SalaryMapper extends BaseMapper<SalaryMain> {

    @SelectProvider(type=SalaryDynaSqlProvider.class,method="findSalarys")
    @Results({
            @Result(id=true,property="salaryId",column="salary_id"),
            @Result(property="empId",column="emp_id"),
            @Result(property="salaryDate",column="salary_date"),
            @Result(property="createTime",column="create_time"),
            @Result(property="updateTime",column="update_time"),
            @Result(property="updateUserId",column="update_user_id"),
            @Result(property="grossPay",column="gross_pay"),
            @Result(property="netPayroll",column="net_payroll"),
            @Result(property="empName",column="emp_name"),
            @Result(property="departId",column="depart_id"),
            @Result(property="empType",column="emp_type")
    })
    public List<SalaryMain> findSalarys(SalaryMain salaryMain);

}
