package ru.netology.diplom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfigurer implements WebMvcConfigurer {
    final private CustomRequestInterceptor customRequestInterceptor;

    public InterceptorConfigurer(@Autowired CustomRequestInterceptor customRequestInterceptor) {
        this.customRequestInterceptor = customRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customRequestInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }
}
