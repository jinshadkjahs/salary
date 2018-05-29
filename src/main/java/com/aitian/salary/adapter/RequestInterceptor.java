package com.aitian.salary.adapter;

import com.aitian.salary.Utils.ConverterSystem;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author yisicheng 2018-05-29
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {

    /**
     * 检查是否登录
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().equals("/index")||request.getRequestURI().indexOf("/login")>-1||request.getRequestURI().indexOf("/static/")>-1){
            //过滤地址
            return true;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute(ConverterSystem.SESSION_USER_KEY )!= null)
            return true;

        // 跳转登录
        String url = "/index";
        response.sendRedirect(url);
        return false;
    }

}