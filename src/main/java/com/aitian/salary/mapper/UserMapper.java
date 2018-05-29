package com.aitian.salary.mapper;

import com.aitian.salary.mapper.provider.UserDynaSqlProvider;
import com.aitian.salary.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //Dao类

    @SelectProvider(type=UserDynaSqlProvider.class,method="findByLogin")
    User findUserByPhoneByLogin(@Param("empId") String empId, @Param("pwd") String pwd);


}
