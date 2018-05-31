package com.aitian.salary.mapper;

import com.aitian.salary.Utils.BaseMapper;
import com.aitian.salary.mapper.provider.UserDynaSqlProvider;
import com.aitian.salary.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    //Dao类

    @SelectProvider(type=UserDynaSqlProvider.class,method="findByLogin")
    @Results({
            @Result(property="userType",column="user_type")
    })
    User findUserByPhoneByLogin(@Param("empId") String empId, @Param("pwd") String pwd);


}
