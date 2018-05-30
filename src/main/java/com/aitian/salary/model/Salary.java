package com.aitian.salary.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "s_salary")
public class Salary {

    @Column(name = "salaryId")
    private Long salaryId;

    @Column(name = "empId")
    private String empId;

    @Column(name = "money")
    private Long money;

    @Column(name = "salaryType")
    private Integer salaryType;

    @Column(name = "salaryDate")
    private String salaryDate;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "updateTime")
    private Date updateTime;

    @Column(name = "updateUserId")
    private Long updateUserId;

    private SalaryType salaryTypeobj;
    /**
     * @return salary_id
     */
    public Long getSalaryId() {
        return salaryId;
    }

    /**
     * @param salaryId
     */
    public void setSalaryId(Long salaryId) {
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
     * @return money
     */
    public Long getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(Long money) {
        this.money = money;
    }

    /**
     * @return salary_type
     */
    public Integer getSalaryType() {
        return salaryType;
    }

    /**
     * @param salaryType
     */
    public void setSalaryType(Integer salaryType) {
        this.salaryType = salaryType;
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

    public SalaryType getSalaryTypeobj() {
        return salaryTypeobj;
    }

    public void setSalaryTypeobj(SalaryType salaryTypeobj) {
        this.salaryTypeobj = salaryTypeobj;
    }
}