package com.team5.surbee.controller;


import com.team5.surbee.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
    @GetMapping("/login-social")
    public String Login(){
        return "user/login";
    }

    @GetMapping("/")
    public String mainpage(HttpSession session){
        SessionUser user = (SessionUser) session.getAttribute("user");

        return "index";
    }

    @GetMapping("/fail-login")
    public String failLogin(){
        System.out.println("로그인 실패!");
        return "index";
    }
}
