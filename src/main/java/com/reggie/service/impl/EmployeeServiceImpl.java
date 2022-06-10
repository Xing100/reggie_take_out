package com.reggie.service.impl;

import com.reggie.pojo.Employee;
import com.reggie.mapper.EmployeeMapper;
import com.reggie.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
