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
@Table(name = "`option`")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String optionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @ToString.Exclude
    private Question question;

    private Option(String optionText, Question question) {
        this.optionText = optionText;
        this.question = question;
    }

    public static Option of(String optionText, Question question) {
        return new Option(optionText, question);
    }

    // 설문 생성 시, 역참조 캡슐화
    public void assignToQuestion(Question question) {
        this.question = question;
    }
}