package com.aitian.salary.model;

import javax.persistence.*;

@Table(name = "s_department")
public class Department {

    @Id
    @Column(name = "depart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Mysql")
    private Integer departid;

    @Column(name = "depart_name")
    private String departName;


    public Integer getDepartid() {
        return departid;
    }

    public void setDepartid(Integer departid) {
        this.departid = departid;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }
}
