package com.team5.surbee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()
//                .requestMatchers("/*").permitAll()  // /survey 하위는 인증 없이 접근 허용
//                .anyRequest().authenticated()              // 그 외 요청은 인증 필요
            )
            .formLogin(withDefaults());  // 기본 로그인 폼 사용

        return http.build();
    }
}