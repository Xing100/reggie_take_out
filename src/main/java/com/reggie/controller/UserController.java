package com.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.reggie.pojo.R;
import com.reggie.pojo.User;
import com.reggie.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author xingzhishan
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    //前端页面没有这个请求
    @PostMapping("sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)){
            String code = "1234";
            session.setAttribute(phone,code);
            return R.success("手机验证码发送成功");
        }
        return R.error("短信发送失败");
    }


    @PostMapping("login")
    public R<User> login(@RequestBody Map map,HttpSession session){

        String phone = map.get("phone").toString();
//        String code = map.get("code").toString();
        String code = "1234";
        session.setAttribute(phone,code);

        Object codeInSession = session.getAttribute(phone);
        if (codeInSession != null && codeInSession.equals(code)){

            User user = iUserService.getOne(new QueryWrapper<User>().eq("phone",phone));
            if (user==null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                iUserService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }

}
