package com.survey.SurveyApp.Dto;

import com.survey.SurveyApp.model.Question;

import java.util.Set;

public class SurveyDto {

    private int survey_id;
    private String name;

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
