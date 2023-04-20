package com.finalproject.receipts.interceptors;

import com.finalproject.receipts.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Data
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private SessionRepository sessionRepository;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new AuthenticationRequestInterceptor(sessionRepository)).addPathPatterns("/api/private/**");
    }
}
