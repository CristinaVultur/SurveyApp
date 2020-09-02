package com.survey.SurveyApp.service;

import com.survey.SurveyApp.Dto.ResponseDto;
import com.survey.SurveyApp.dao.SurveyDAO;
import com.survey.SurveyApp.dao.UserDAO;
import com.survey.SurveyApp.model.Response;
import com.survey.SurveyApp.model.Survey;
import com.survey.SurveyApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SurveyDAO surveyDAO;

    @Transactional
    @Override
    public List<User> get() {
        return userDAO.get();
    }
    @Transactional
    @Override
    public User get(String name) {
        return userDAO.get(name);
    }

    @Override
    public List<Survey> getSurveys(String name) {
        return userDAO.getSurveys(name);
    }



    @Transactional
    @Override
    public Survey createSurvey(Survey survey, String name) {
        return surveyDAO.createSurvey(survey,name);
    }

    //open survey that he created
    @Transactional
    @Override
    public Survey openSurvey(int id,String name) {
        return surveyDAO.openSurvey(id, name);
    }


    @Override
    public Set<Response> getResponses(String name, int id) {
        return userDAO.getResponses(name,id);
    }

    //close survey from opened ones
    @Override
    public Survey closeSurvey(int id,String name) {
        return surveyDAO.closeSurvey(id,name);
    }

    @Transactional
    @Override
    public Survey getOneSurvey(String name,int id) {
        return surveyDAO.getOneSurvey(name,id);
    }

    //@Transactional
    @Override
    public Set<Response> addResponses(String name, int []responsesGivens) {
        return userDAO.addResponses(name, responsesGivens);
    }

}
