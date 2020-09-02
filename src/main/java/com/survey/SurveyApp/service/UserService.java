package com.survey.SurveyApp.service;

import com.survey.SurveyApp.Dto.ResponseDto;
import com.survey.SurveyApp.model.Response;
import com.survey.SurveyApp.model.User;
import com.survey.SurveyApp.model.Survey;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> get();

    User get(String name);
    List<Survey> getSurveys(String name);
    Survey createSurvey(Survey survey,String name);

    Survey openSurvey(int id,String name);


    Set<Response> getResponses(String name, int id);

   // public UserDetails loadUserByUsername(String username);
    Survey closeSurvey(int id,String name);

    Survey getOneSurvey(String name,int id);

    Set<Response> addResponses(String name, int []responsesGivens);
}
