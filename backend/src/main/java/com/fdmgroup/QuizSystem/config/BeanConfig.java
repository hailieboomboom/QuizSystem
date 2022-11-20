package com.fdmgroup.QuizSystem.config;

import com.fdmgroup.QuizSystem.dto.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    /**
     * Create a bean for CustomUserDetails.
     * @return CustomUserDetails.
     */
    @Bean
    public CustomUserDetails customUserDetails() { return new CustomUserDetails(); }
}
