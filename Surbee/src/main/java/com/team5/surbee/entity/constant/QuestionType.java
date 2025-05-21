package com.team5.surbee.entity.constant;

public enum QuestionType {
    MULTIPLE_CHOICE, CHECKBOX, SHORT_ANSWER, LONG_ANSWER;

    public boolean isText() {
        return this == SHORT_ANSWER || this == LONG_ANSWER;
    }
}
