package com.reggie;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.reggie.mapper.EmployeeMapper;
import com.reggie.pojo.Employee;
import com.reggie.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class db {

    @Autowired
    private  EmployeeMapper employeeMapper;
    @Autowired
    private ICategoryService iCategoryService;
    @Test
    public void test(){
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("name", "管理员"));
        System.out.println(employee);
    }



}
