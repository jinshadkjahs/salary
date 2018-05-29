package com.aitian.salary.model;

import javax.persistence.*;

@Table(name = "s_user_type")
public class UserType {

    @Id
    @Column(name = "user_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Mysql")
    private Integer userType;

    @Column(name = "type_name")
    private String typeName;


    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
