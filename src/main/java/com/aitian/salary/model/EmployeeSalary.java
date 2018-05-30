package com.aitian.salary.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class EmployeeSalary {

    private String empId;
    private String empName;
    private String empPhone;
    private String empCardNum;
    private String empType;
    private Date waltzDate;
    private Integer departId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private int baseSalary;

    private List<Salary> salaryList;

    public EmployeeSalary() {
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

    public void setDepartInt(int departId) {
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

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    public List<Salary> getSalaryList() {
        return salaryList;
    }

    public void setSalaryList(List<Salary> salaryList) {
        this.salaryList = salaryList;
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
}
