package com.survey.SurveyApp.Dto;

import java.util.Set;

public class RespondendDto {
    private String username;
    private Set<ResponseDto> responses;

    public RespondendDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<ResponseDto> getResponses() {
        return responses;
    }

    public void setResponses(Set<ResponseDto> responses) {
        this.responses = responses;
    }
}
