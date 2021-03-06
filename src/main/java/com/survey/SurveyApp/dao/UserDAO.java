package com.survey.SurveyApp.dao;

import com.survey.SurveyApp.Dto.ResponseDto;
import com.survey.SurveyApp.model.Response;
import com.survey.SurveyApp.model.Survey;
import com.survey.SurveyApp.model.User;

import java.util.List;
import java.util.Set;

public interface UserDAO {

    User get(String name);

    List<Survey> getSurveys(String name);

    User findByUsername(String username);

    Set<Response> getResponses(String name, int id);

    Set<Response> addResponses(String name, int []responsesGiven,int survey_id);
}
