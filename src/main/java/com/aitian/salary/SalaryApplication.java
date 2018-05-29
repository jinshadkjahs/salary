package com.aitian.salary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author yisicheng 2018-05-29
 * SpringBoot 启动类
 */

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.aitian.mapper")
public class SalaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalaryApplication.class, args);
    }
}
