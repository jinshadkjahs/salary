package com.aitian.salary.service.impl;

import com.aitian.salary.mapper.UserMapper;
import com.aitian.salary.model.User;
import com.aitian.salary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    @Override
    public User findUserByLogin(String empId, String pwd){

        return userMapper.findUserByPhoneByLogin(empId, pwd);

    }
}
