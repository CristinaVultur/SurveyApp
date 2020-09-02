package com.survey.SurveyApp.dao;

import com.survey.SurveyApp.model.Survey;

import java.util.List;


public interface SurveyDAO {

    String createSurvey(Survey survey, String name);

    StringBuilder openSurvey(int id,String name);

    String closeSurvey(int id,String name);

    StringBuilder getOpenedSurveys();

    StringBuilder getOneOpenedSurvey(int id);


}
