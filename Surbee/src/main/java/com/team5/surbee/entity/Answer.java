package com.team5.surbee.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id")
    private Submission submission;

    private Answer(Question question, Option option, String answerText, Submission submission) {
        this.question = question;
        this.option = option;
        this.answerText = answerText;
        this.submission = submission;
    }

    // 🔸 단답형 / 장문형 답변 생성용 팩토리
    public static Answer textAnswer(Question question, String answerText, Submission submission) {
        return new Answer(question, null, answerText, submission);
    }

    // 🔸 객관식 / 체크박스 답변 생성용 팩토리
    public static Answer optionAnswer(Question question, Option option, Submission submission) {
        return new Answer(question, option, null, submission);
    }

    public void assignToSubmission(Submission submission) {
        this.submission = submission;
    }
}