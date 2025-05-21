package com.team5.surbee.controller;

import com.team5.surbee.dto.SessionUserDto;
import com.team5.surbee.dto.request.SurveyAnswerRequest;
import com.team5.surbee.dto.request.SurveyCreateRequest;
import com.team5.surbee.dto.response.survey.SurveyResultResponse;
import com.team5.surbee.dto.response.survey.SurveyVoteResponse;
import com.team5.surbee.service.SurveyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{surveyId}")
    public String deleteSurvey(@PathVariable("surveyId") Integer surveyId, HttpSession session) {
        SessionUserDto user = (SessionUserDto) session.getAttribute("user");
        surveyService.deleteSurvey(surveyId, user.id());
        return "redirect:/"; // 이후 마이 페이지로 수정
    }

//    @GetMapping("/my")
//    public String getMySurveys(HttpSession session, Model model) {
//        SessionUserDto user = (SessionUserDto) session.getAttribute("user");
//        List<SurveySummaryResponse> responses = surveyService.getSurveysByUser(user.id());
//        model.addAttribute("surveys", responses);
//        return "redirect:/"; // 이후 마이 페이지로 수정
//    }

    @GetMapping("/{surveyId}/answer")
    public String showAnswerPage(@PathVariable("surveyId") Integer surveyId, Model model) {
        SurveyVoteResponse response = surveyService.getSurveyVote(surveyId);
        model.addAttribute("survey", response);
        log.info("response={}", response);
        return "survey/answer";
    }

    @PostMapping("/{surveyId}/answer")
    public String submitSurvey(@PathVariable("surveyId") Integer surveyId,
                               @ModelAttribute SurveyAnswerRequest request,
                               HttpSession session) {
        log.info("Controller : 설문 응답 request={}", request);
        SessionUserDto user = (SessionUserDto) session.getAttribute("user");
        return "redirect:/survey/" + surveyId + "/answer";
    }


    @GetMapping("/{surveyId}/result")
    public String getSurveyResult(@PathVariable("surveyId") Integer surveyId, Model model) {
        log.info("Controller : 응답 결과 출력 (id = {})", surveyId);
        SurveyResultResponse response = surveyService.getSurveyResult(surveyId);
        model.addAttribute("survey", response);
        log.info("Controller : response = {})", response);
        return "result/stats";
    }
}
