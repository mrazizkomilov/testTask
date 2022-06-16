package com.task.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilterConfig {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(jwtTokenFilter);
        frb.addUrlPatterns("/profile/adm");
        frb.addUrlPatterns("/profile/adm/*");
        frb.addUrlPatterns("/profile/detail");
        frb.addUrlPatterns("/company/*");
        frb.addUrlPatterns("/profile/updateEmail");
        frb.addUrlPatterns("/profile/updateEmail/*");
        return frb;
    }
}
