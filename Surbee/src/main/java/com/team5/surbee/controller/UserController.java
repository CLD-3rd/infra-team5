package com.team5.surbee.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
