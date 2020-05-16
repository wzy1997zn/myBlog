package com.zywang.myblog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()) // add a interceptor
                .addPathPatterns("/admin/**") // all the paths like this
                .excludePathPatterns("/admin","/admin/login");
        // but admin itself and the form submit path should be exclude to avoid dead loop.
    }
}
