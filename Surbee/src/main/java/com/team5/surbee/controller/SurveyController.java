package com.team5.surbee.controller;

import com.team5.surbee.dto.SessionUserDto;
import com.team5.surbee.dto.request.SurveyCreateRequest;
import com.team5.surbee.dto.response.survey.SurveyResultResponse;
import com.team5.surbee.dto.response.survey.SurveySummaryResponse;
import com.team5.surbee.dto.response.survey.SurveyVoteResponse;
import com.team5.surbee.service.SurveyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{surveyId}")
    public String getSurveyVote(@PathVariable Integer surveyId, Model model) {
        SurveyVoteResponse response = surveyService.getSurveyVote(surveyId);
        model.addAttribute("survey", response);
        return "survey/answer"; // 이후 설문 응답 페이지로 수정
    }

    @DeleteMapping("/{surveyId}")
    public String deleteSurvey(@PathVariable Integer surveyId, HttpSession session) {
        SessionUserDto user = (SessionUserDto) session.getAttribute("user");
        surveyService.deleteSurvey(surveyId, user.id());
        return "redirect:/"; // 이후 마이 페이지로 수정
    }

    @GetMapping("/my")
    public String getMySurveys(HttpSession session, Model model) {
        SessionUserDto user = (SessionUserDto) session.getAttribute("user");
        List<SurveySummaryResponse> responses = surveyService.getSurveysByUser(user.id());
        model.addAttribute("surveys", responses);
        return "redirect:/"; // 이후 마이 페이지로 수정
    }

    @GetMapping("/answer")
    public String showAnswerPage(Model model) {
        // 실제로는 설문 데이터를 서비스에서 불러와야 합니다.
        return "survey/answer";
    }


    @GetMapping("/{surveyId}/result")
    public String getSurveyResult(@PathVariable Integer surveyId, Model model) {
        log.info("Controller : 응답 결과 출력 (id = {})", surveyId);
        SurveyResultResponse response = surveyService.getSurveyResult(surveyId);
        model.addAttribute("survey", response);
        log.info("Controller : response = {})", response);
        return "result/stats";
    }
}
