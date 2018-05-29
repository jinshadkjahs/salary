package com.aitian.salary.model;

import javax.persistence.*;

@Table(name = "s_emp_type")
public class EmployeeType {

    @Id
    @Column(name = "emp_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Mysql")
    private Integer empType;

    @Column(name = "emp_type_name")
    private String typeName;


    public Integer getEmpType() {
        return empType;
    }

    public void setEmpType(Integer empType) {
        this.empType = empType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
