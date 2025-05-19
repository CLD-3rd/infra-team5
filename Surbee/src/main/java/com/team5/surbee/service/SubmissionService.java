package com.team5.surbee.service;

import com.team5.surbee.repository.AnswerRepository;
import com.team5.surbee.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    // Answer 관련 처리는 여기
    private final SubmissionRepository submissionRepository;
    private final AnswerRepository answerRepository;
}
