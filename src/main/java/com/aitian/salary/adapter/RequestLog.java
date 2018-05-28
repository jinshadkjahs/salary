package com.aitian.salary.adapter;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestLog extends HandlerInterceptorAdapter {

    /**
     * 前置检查
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().equals("/index")||request.getRequestURI().indexOf("/login")>-1||request.getRequestURI().indexOf("/static/")>-1){
            return true;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null)
            return true;

        // 跳转登录
        String url = "/index";
        response.sendRedirect(url);
        return false;
    }

}