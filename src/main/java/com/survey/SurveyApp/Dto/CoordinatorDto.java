package com.survey.SurveyApp.Dto;

import javax.swing.plaf.SeparatorUI;
import java.util.Set;

public class CoordinatorDto {

    private String username;

    private Set<SurveyDto> surveys;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<SurveyDto> getSurveys() {
        return surveys;
    }

    public void setSurveys(Set<SurveyDto> surveys) {
        this.surveys = surveys;
    }
}
