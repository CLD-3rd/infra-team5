package com.team5.surbee.security;

import com.team5.surbee.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig{
    final private CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/login-social", "/fail-login","/actuator/**").permitAll()
                        .requestMatchers("/surveys/create").authenticated()
                        .requestMatchers("/surveys/*").permitAll()
                        .anyRequest().authenticated()
                );

        http
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login-social")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // 모든 provider 공통으로 사용
                                .oidcUserService((userRequest) -> (org.springframework.security.oauth2.core.oidc.user.OidcUser) customOAuth2UserService.loadUser(userRequest)) // OIDC도 강제 진입
                        )
                        .defaultSuccessUrl("/", true)
                        .failureHandler((request, response, exception) -> {
                            exception.printStackTrace();  // 예외 스택 트레이스 출력
                            response.sendRedirect("/fail-login");  // 실패 시 에러 페이지로
                        })
                        .permitAll()
                )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
            );

        return http.build();
    }


}