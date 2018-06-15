package com.aitian.salary.service;

import com.aitian.salary.model.User;

import java.util.List;

public interface UserService {

    User findUserByLogin(String empid, String pwd);

    void updateUser(User user);
}