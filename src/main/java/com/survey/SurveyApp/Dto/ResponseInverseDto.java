package com.survey.SurveyApp.Dto;

import java.util.Set;

public class ResponseInverseDto {

    private String text;

    private Set<RespondendInverseDto> respondents;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<RespondendInverseDto> getRespondents() {
        return respondents;
    }

    public void setRespondents(Set<RespondendInverseDto> respondents) {
        this.respondents = respondents;
    }
}
