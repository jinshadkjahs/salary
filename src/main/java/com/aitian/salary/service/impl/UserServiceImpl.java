package com.aitian.salary.service.impl;

import com.aitian.salary.mapper.UserMapper;
import com.aitian.salary.model.User;
import com.aitian.salary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
/**
 * @Author yisicheng 2018-05-29
 * 一组操作需要加事务
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    @Override
    public User findUserByLogin(String empId, String pwd){

        return userMapper.findUserByPhoneByLogin(empId, pwd);

    }


    @Override
    public void updateUser(User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("empId",user.getEmpId());
        userMapper.updateByExample(user, example);
    }
}
