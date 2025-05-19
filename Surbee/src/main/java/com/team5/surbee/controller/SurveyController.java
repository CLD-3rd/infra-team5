package com.team5.surbee.controller;

import ch.qos.logback.core.model.Model;
import com.team5.surbee.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/surveys")
public class SurveyController {

    @GetMapping("/create")
    public String showCreateSurveyPage(Model model) {return "survey/create";}

}
