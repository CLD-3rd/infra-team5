package com.team5.surbee.controller;

import com.team5.surbee.dto.response.SurveyMainResponse;
import com.team5.surbee.service.SurveyService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final SurveyService surveyService;

    @GetMapping("/")
    public String getMainSurveyList(Model model) {
        SurveyMainResponse response = surveyService.getMainSurveyList();
        model.addAttribute("surveys", response);
        return "index";  // src/main/resources/templates/index.html
    }
}
