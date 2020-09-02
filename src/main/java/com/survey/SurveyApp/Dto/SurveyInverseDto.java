package com.survey.SurveyApp.Dto;

import java.util.Set;

public class SurveyInverseDto {
    private String name;
    private Set<QuestionsInverseDto> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<QuestionsInverseDto> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionsInverseDto> questions) {
        this.questions = questions;
    }
}
