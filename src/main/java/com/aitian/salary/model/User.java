package com.aitian.salary.model;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "s_user")
public class User {

    @Column(name = "empid")
    private String empId;

    @Column(name = "password")
    private String password;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}