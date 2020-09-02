package com.survey.SurveyApp.Dto;

import java.util.Set;

public class QuestionsDto {

    private String text;
    //private Set<ResponseDto> responses;

    private SurveyDto survey;


    public SurveyDto getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyDto survey) {
        this.survey = survey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
