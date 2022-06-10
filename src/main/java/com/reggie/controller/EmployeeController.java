package com.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.pojo.Employee;
import com.reggie.pojo.R;
import com.reggie.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService iEmployeeService;


    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录接口")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        Employee emp = iEmployeeService.getOne(new QueryWrapper<Employee>().eq("username", employee.getUsername()));
        if (emp == null){
            return R.error("登录失败");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }
        if (emp.getStatus() == 0){
            return R.error("账户已禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @ApiOperation("退出接口")
    @PostMapping("/logout")
    public R logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");

    }

    /**
     * 添加员工
     * @param employee
     * @param request
     * @return
     */

    @ApiOperation("添加员工")
    @PostMapping
    public R save(@RequestBody Employee employee,HttpServletRequest request ){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        iEmployeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);
        Page<Employee> pageInfo = new Page(page,pageSize);
        iEmployeeService.page(pageInfo,new QueryWrapper<Employee>()
                .like(StringUtils.isNotEmpty(name),"name",name)
                .orderByAsc("update_time"));
        return R.success(pageInfo);
    }

    /**
     * 修改员工信息
     * @param employee
     * @return
     */
    @ApiOperation("修改员工信息")
    @PutMapping
    public R update(HttpServletRequest request,@RequestBody Employee employee){
        iEmployeeService.updateById(employee);
        return R.success("修改信息成功");
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @ApiOperation("查询员工信息")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id")Long id){
        Employee employee = iEmployeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }





}
