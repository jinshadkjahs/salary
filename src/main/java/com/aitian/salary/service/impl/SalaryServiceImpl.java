package com.aitian.salary.service.impl;

import com.aitian.salary.Utils.ReadExcel;
import com.aitian.salary.model.Salary;
import com.aitian.salary.service.SalaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service(value = "salaryService")
public class SalaryServiceImpl implements SalaryService {

    @Transactional
    @Override
    public boolean batchImport(String name, MultipartFile file) {
        boolean b = false;
        //创建处理EXCEL
        ReadExcel readExcel=new ReadExcel();
        //解析excel，获取客户信息集合。
        List<Salary> customerList = readExcel.getExcelInfo(name ,file);

        if(customerList != null){
            b = true;
        }

        //迭代添加客户信息（注：实际上这里也可以直接将customerList集合作为参数，在Mybatis的相应映射文件中使用foreach标签进行批量添加。）
        return b;
    }

}
