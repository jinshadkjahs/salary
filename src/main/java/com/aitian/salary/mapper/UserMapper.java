package com.aitian.salary.mapper;

import com.aitian.salary.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM S_USER WHERE empid = #{empId} and password = #{pwd}")
    User findUserByPhoneByLogin(@Param("empId") String empId, @Param("pwd") String pwd);


}
