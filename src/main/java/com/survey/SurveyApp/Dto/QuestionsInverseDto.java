package com.survey.SurveyApp.Dto;

import java.util.Set;

public class QuestionsInverseDto {

    private String text;
    private Set<ResponseInverseDto> responses;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<ResponseInverseDto> getResponses() {
        return responses;
    }

    public void setResponses(Set<ResponseInverseDto> responses) {
        this.responses = responses;
    }
}
