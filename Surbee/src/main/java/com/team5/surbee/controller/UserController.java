package com.team5.surbee.controller;


import com.team5.surbee.dto.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
    @GetMapping("/login-social")
    public String Login(HttpServletRequest request, Model model){
        model.addAttribute("requestURI", request.getRequestURI());
        return "user/login";
    }

    @GetMapping("/fail-login")
    public String failLogin(){
        return "error/error";
    }
}
