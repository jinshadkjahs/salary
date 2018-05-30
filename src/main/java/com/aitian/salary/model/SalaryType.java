package com.aitian.salary.model;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "s_salary_type")
public class SalaryType {
    /**
     *
     */
    @Column(name = "salaryType")
    private Integer salaryType;

    /**
     *
     */
    @Column(name = "salaryName")
    private String salaryName;

    /**
     * 是加 还是扣
     */
    @Column(name = "type")
    private String type;

    /**
     * 员工类型是否有这项工资项
     */
    @Column(name = "empType")
    private String empType;

    /**
     * 
     * @return salary_type 
     */
    public Integer getSalaryType() {
        return salaryType;
    }

    /**
     * 
     * @param salaryType 
     */
    public void setSalaryType(Integer salaryType) {
        this.salaryType = salaryType;
    }

    /**
     * 
     * @return salary_name 
     */
    public String getSalaryName() {
        return salaryName;
    }

    /**
     * 
     * @param salaryName 
     */
    public void setSalaryName(String salaryName) {
        this.salaryName = salaryName == null ? null : salaryName.trim();
    }

    /**
     * 是加 还是扣
     * @return type 是加 还是扣
     */
    public String getType() {
        return type;
    }

    /**
     * 是加 还是扣
     * @param type 是加 还是扣
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 员工类型是否有这项工资项
     * @return emp_type 员工类型是否有这项工资项
     */
    public String getEmpType() {
        return empType;
    }

    /**
     * 员工类型是否有这项工资项
     * @param empType 员工类型是否有这项工资项
     */
    public void setEmpType(String empType) {
        this.empType = empType == null ? null : empType.trim();
    }
}