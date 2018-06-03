package com.aitian.salary.model;

import javax.persistence.*;

@Table(name = "s_salary_type_emp")
public class SalaryTypeEmp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Mysql")
    @Column(name = "salary_type_emp_id")
    private Integer salaryTypeEmpId;

    @Column(name = "salary_id")
    private Integer salaryId;

    @Column(name = "money")
    private Long money;

    @Column(name = "salary_type")
    private Integer salaryType;

    public Integer getSalaryTypeEmpId() {
        return salaryTypeEmpId;
    }

    public void setSalaryTypeEmpId(Integer salaryTypeEmpId) {
        this.salaryTypeEmpId = salaryTypeEmpId;
    }

    public Integer getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Integer salaryId) {
        this.salaryId = salaryId;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Integer getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(Integer salaryType) {
        this.salaryType = salaryType;
    }
}