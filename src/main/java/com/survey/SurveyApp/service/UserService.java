package com.survey.SurveyApp.service;

import com.survey.SurveyApp.model.User;
import com.survey.SurveyApp.model.Survey;

import java.util.List;

public interface UserService {
    List<User> get();

    User get(String name);
    String getString(String name);

    void save(User coordinator);

    void delete(int id);

    String createSurvey(Survey survey,String name);

    StringBuilder openSurvey(int id,String name);

    StringBuilder getOpenedSurveys();

    StringBuilder getResponses(String name);

   // public UserDetails loadUserByUsername(String username);
    String closeSurvey(int id,String name);

    StringBuilder getOneOpenedSurvey(int id);

    String addResponses(String name, int []responsesGivens);
}
