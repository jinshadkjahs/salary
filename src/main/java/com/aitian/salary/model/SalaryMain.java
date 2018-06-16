package com.aitian.salary.model;

import java.util.Date;
import javax.persistence.*;
import java.util.List;

@Table(name = "s_salary_main")
public class SalaryMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Mysql")
    @Column(name = "salary_id")
    private Integer salaryId;

    @Column(name = "emp_id")
    private String empId;

    @Column(name = "salary_date")
    private String salaryDate;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 应发工资
     */
    @Column(name = "gross_pay")
    private Long grossPay;

    /**
     * 实发工资
     */
    @Column(name = "net_payroll")
    private Long netPayroll;


    /**
     * @return salary_id
     */
    public Integer getSalaryId() {
        return salaryId;
    }

    /**
     * @param salaryId
     */
    public void setSalaryId(Integer salaryId) {
        this.salaryId = salaryId;
    }

    /**
     * @return emp_id
     */
    public String getEmpId() {
        return empId;
    }

    /**
     * @param empId
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    /**
     * @return salary_date
     */
    public String getSalaryDate() {
        return salaryDate;
    }

    /**
     * @param salaryDate
     */
    public void setSalaryDate(String salaryDate) {
        this.salaryDate = salaryDate;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return update_user_id
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * @param updateUserId
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }


    public Long getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(Long grossPay) {
        this.grossPay = grossPay;
    }

    public Long getNetPayroll() {
        return netPayroll;
    }

    public void setNetPayroll(Long netPayroll) {
        this.netPayroll = netPayroll;
    }

    @Transient
    private List<SalaryTypeEmp> salaryTypeEmpList;

    @Transient
    private Employee employee;

    @Transient
    private String empName;
    @Transient
    private String departId;
    @Transient
    private String empType;

    public List<SalaryTypeEmp> getSalaryTypeEmpList() {
        return salaryTypeEmpList;
    }

    public void setSalaryTypeEmpList(List<SalaryTypeEmp> salaryTypeEmpList) {
        this.salaryTypeEmpList = salaryTypeEmpList;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }
    @Transient
    public List<BonusInfo> bonusInfos;

    public List<BonusInfo> getBonusInfos() {
        return bonusInfos;
    }

    public void setBonusInfos(List<BonusInfo> bonusInfos) {
        this.bonusInfos = bonusInfos;
    }
}
