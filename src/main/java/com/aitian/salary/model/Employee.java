package com.aitian.salary.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Table(name = "s_employee")
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Mysql")
    @Column(name = "empid")
    private String empId;
    @Column(name = "emp_name")
    private String empName;
    @Column(name = "emp_phone")
    private String empPhone;
    @Column(name = "emp_card_num")
    private String empCardNum;
    @Column(name = "emp_type")
    private String empType;
    @Column(name = "waltz_date")
    private Date waltzDate;
    @Column(name = "depart_id")
    private Integer departId;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_time")
    private Timestamp updateTime;
    @Column(name = "base_salary")
    private Integer baseSalary;
    @Transient
    private User user;
    public Employee() {
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpCardNum() {
        return empCardNum;
    }

    public void setEmpCardNum(String empCardNum) {
        this.empCardNum = empCardNum;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public Date getWaltzDate() {
        return waltzDate;
    }

    public void setWaltzDate(Date waltzDate) {
        this.waltzDate = waltzDate;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartInt(Integer departId) {
        this.departId = departId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Integer baseSalary) {
        this.baseSalary = baseSalary;
    }

    public void setDepartId(int departId) {
        this.departId = departId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empPhone='" + empPhone + '\'' +
                ", empCardNum='" + empCardNum + '\'' +
                ", empType='" + empType + '\'' +
                ", waltzDate=" + waltzDate +
                ", departId=" + departId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", baseSalary=" + baseSalary +
                '}';
    }
    @Transient
    private String empTypeStr;
    @Transient
    private String departIdStr;

    public String getEmpTypeStr() {
        return empTypeStr;
    }

    public void setEmpTypeStr(String empTypeStr) {
        this.empTypeStr = empTypeStr;
    }

    public String getDepartIdStr() {
        return departIdStr;
    }

    public void setDepartIdStr(String departIdStr) {
        this.departIdStr = departIdStr;
    }
}
