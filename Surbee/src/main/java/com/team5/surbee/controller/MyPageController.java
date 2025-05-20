package com.team5.surbee.controller;

import com.team5.surbee.common.exception.SurveyException;
import com.team5.surbee.dto.SessionUserDto;
import com.team5.surbee.dto.response.survey.SurveySummaryResponse;
import com.team5.surbee.entity.User;
import com.team5.surbee.service.SurveyService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/api/survey")
public class MyPageController {

    private final SurveyService surveyService;

    /**
     * 마이페이지 설문 목록 조회
     */
    
    @GetMapping("/user/mypage")
    public String getMySurveys(HttpSession session, Model model) {
        SessionUserDto user = (SessionUserDto) session.getAttribute("user");
        List<SurveySummaryResponse> responses = surveyService.getSurveysByUser(user.id());
        model.addAttribute("surveys", responses);
        return "/member/mypage"; 
    }
    
    
    /**
     * 설문 종료 API
     */
    @PostMapping("/survey/{id}/close")
    public ResponseEntity<Void> endSurvey(@PathVariable("id") Integer id, HttpSession session) {
        SessionUserDto user = (SessionUserDto) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            surveyService.closeSurvey(id, user.id());
            return ResponseEntity.ok().build();
        } catch (SurveyException e) {
            return ResponseEntity.status(e.getErrorCode().getStatus()).build();
        }
    }
    
    
    

    /**
     * 설문 삭제 API (다중)
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteSurveys(@RequestBody Map<String, List<Integer>> requestBody,
                                              HttpSession session) {
        SessionUserDto user = (SessionUserDto) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        List<Integer> ids = requestBody.get("ids");

        for (Integer surveyId : ids) {
            try {
                surveyService.deleteSurvey(surveyId, user.id());
            } catch (SurveyException e) {
                return ResponseEntity.status(e.getErrorCode().getStatus()).build();
            }
        }

        return ResponseEntity.ok().build();
    }
}
