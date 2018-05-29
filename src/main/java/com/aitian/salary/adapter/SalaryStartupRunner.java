package com.aitian.salary.adapter;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.service.LoadingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 服务启动执行
 * @Author yisicheng 2018-05-29
 */
@Component
public class SalaryStartupRunner implements CommandLineRunner {

    @Resource
    private LoadingService loadingService;

    @Override
    public void run(String... args) throws Exception {

        /**
         * 加载所有科室信息
         */
        ConverterSystem.ALL_DEPARTMENT = loadingService.selectAllDepartment();

        /**
         * 加载所有员工类型信息
         */
        ConverterSystem.ALL_EMPLOYEE_TYPE = loadingService.selectAllEmployeeType();

        /**
         * 加载所有工资类型信息
         */
        ConverterSystem.ALL_SALARY_TYPE = loadingService.selectAllSalaryType();

        /**
         * 加载所有用户类型
         */
        ConverterSystem.ALL_USER_TYPE = loadingService.selectAllUserType();

    }

}