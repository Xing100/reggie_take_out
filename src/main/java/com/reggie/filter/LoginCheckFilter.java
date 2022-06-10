package com.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.reggie.common.BaseContext;
import com.reggie.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //拿到请求的URI
        String requestURI = request.getRequestURI();

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
//                "/doc.html",
//                "/webjars/**",
//                "/swagger-resources/**",
//                "/v2/api-docs/**",
//                "/ws/**"
        };
        //判断你本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //如果不需要处理，则直接放行
        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        //判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee")!=null){
            Long empid = (Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empid);
            filterChain.doFilter(request,response);
            return;
        }

        if (request.getSession().getAttribute("user")!=null){
            Long userid = (Long)request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userid);
            filterChain.doFilter(request,response);
            return;
        }


        //如果未登录则返回登录页面，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param reqeustURI
     * @return
     */
    public boolean check(String[] urls,String reqeustURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, reqeustURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
