package com.survey.SurveyApp.dao;

import com.survey.SurveyApp.model.Survey;

import java.util.List;


public interface SurveyDAO {

    Survey createSurvey(Survey survey, String name);

    Survey openSurvey(int id,String name);

    Survey closeSurvey(int id,String name);

    Survey getOneSurvey(String name,int id);


}
