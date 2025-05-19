package com.team5.surbee.controller;

import ch.qos.logback.core.model.Model;
import com.team5.surbee.dto.SessionUserDto;
import com.team5.surbee.dto.request.SurveyCreateRequest;
import com.team5.surbee.service.SurveyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("/create")
    public String showCreateSurveyPage(Model model) {
        return "survey/create";
    }

    @PostMapping("/create")
    public String createSurvey(HttpSession httpSession, @ModelAttribute SurveyCreateRequest request) {
        SessionUserDto user = (SessionUserDto) httpSession.getAttribute("user");
        surveyService.createSurvey(request.toDto(user));
        return "redirect:/";
    }

}
