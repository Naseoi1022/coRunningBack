package com.tjoeun.corunning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())       // CSRF 끄기
                .cors(Customizer.withDefaults())     // CORS 기본 설정
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()    // 모두 허용
                )
                .formLogin(login -> login.disable())  // 로그인 폼 비활성화
                .httpBasic(basic -> basic.disable()); // Basic Auth 비활성화

        return http.build();
    }
}
