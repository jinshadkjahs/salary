package com.aitian.salary.model;
import javax.persistence.Table;
import javax.persistence.*;

@Table(name = "s_bonus_info")
public class BonusInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Mysql")
    @Column(name = "id")
    private Integer id;
    @Column(name = "emp_id")
    private String empId;
    @Column(name = "emp_name")
    private String empName;
    @Column(name = "money")
    private String money;
    @Column(name = "cont")
    private String cont;
    @Column(name = "manage_depart")
    private String manageDepart;
    @Column(name = "salary_id")
    private Integer salaryId;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return emp_id 
     */
    public String getEmpId() {
        return empId;
    }

    /**
     * 
     * @param empId 
     */
    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    /**
     * 
     * @return emp_name 
     */
    public String getEmpName() {
        return empName;
    }

    /**
     * 
     * @param empName 
     */
    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    /**
     * 
     * @return money 
     */
    public String getMoney() {
        return money;
    }

    /**
     * 
     * @param money 
     */
    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    /**
     * 
     * @return cont 
     */
    public String getCont() {
        return cont;
    }

    /**
     * 
     * @param cont 
     */
    public void setCont(String cont) {
        this.cont = cont == null ? null : cont.trim();
    }

    /**
     * 
     * @return manage_depart 
     */
    public String getManageDepart() {
        return manageDepart;
    }

    /**
     * 
     * @param manageDepart 
     */
    public void setManageDepart(String manageDepart) {
        this.manageDepart = manageDepart == null ? null : manageDepart.trim();
    }

    public Integer getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Integer salaryId) {
        this.salaryId = salaryId;
    }
}