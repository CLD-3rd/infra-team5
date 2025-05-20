package com.team5.surbee.entity;

import com.team5.surbee.entity.constant.QuestionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Column(nullable = false)
    private Boolean isRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    @ToString.Exclude
    private Survey survey;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Option> options = new ArrayList<>();

    private Question(String questionText, QuestionType questionType, Boolean isRequired, Survey survey, List<Option> options) {
        this.questionText = questionText;
        this.questionType = questionType;
        this.isRequired = isRequired;
        this.survey = survey;
        this.options = options;
    }

    public static Question of(String questionText, QuestionType questionType, Boolean isRequired, Survey survey, List<Option> options) {
        return new Question(questionText, questionType, isRequired, survey, options);
    }

    public void assignToSurvey(Survey survey) {
        this.survey = survey;
    }
}