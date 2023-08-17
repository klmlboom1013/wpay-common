package com.wpay.common.templates.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(value = "templatesSecurityConfig")
public class SecurityConfig {

    /**
     * Spring Security 설정
     */
    @Bean(value = "templatesSecurityFilterChain")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 접근 권한 제어 예외 URI 등록
        http.antMatcher("/*");
        return http.build();
    }
}
