package com.team5.surbee.security;

import com.team5.surbee.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig{
    final private CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/login-social", "/fail-login","/good").permitAll()
                        .anyRequest().authenticated()
                );

        http
                .oauth2Login(oauth2 -> oauth2
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
                );
//            .logout(logout -> logout
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/")
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID")
//                    .clearAuthentication(true)
//            );

        return http.build();
    }


}