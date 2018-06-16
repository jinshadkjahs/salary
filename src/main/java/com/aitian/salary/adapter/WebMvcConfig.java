package com.aitian.salary.adapter;

import com.aitian.salary.Utils.ConverterSystem;
import com.aitian.salary.Utils.ReponseCode;
import com.aitian.salary.controller.response.BaseResponse;
import com.aitian.salary.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yisicheng 2018-05-29
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源映射
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    // 增加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(new RequestInterceptor());
        // 排除配置
//        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("/");
        addInterceptor.excludePathPatterns("/static/**");
        addInterceptor.excludePathPatterns("/user/login");
        // 拦截配置
        addInterceptor.addPathPatterns("/**");

        InterceptorRegistration managerInterceptor = registry.addInterceptor(new HandlerInterceptorAdapter(){
            /**
             * 检查是否有权限访问
             */
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
                User user = (User) request.getSession().getAttribute(ConverterSystem.SESSION_USER_KEY);
                if(user != null){
                    if("0".equals(user.getUserType())){
                       return true;
                    }
                }

                BaseResponse br = new BaseResponse();
                br.setCode(ReponseCode.NOT_ALLOW);
                response.getWriter().write(new ObjectMapper().writeValueAsString(br));

                return false;
            }
        });
        List<String> list = new ArrayList<>();
        list.add("/emp/*");
        list.add("/employee/*");
        list.add("/salary/*");
        list.add("/salarymanager/*");
        managerInterceptor.addPathPatterns(list);
    }
}