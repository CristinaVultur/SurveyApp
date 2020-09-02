package com.survey.SurveyApp.service;

import com.survey.SurveyApp.dao.SurveyDAO;
import com.survey.SurveyApp.dao.UserDAO;
import com.survey.SurveyApp.model.Survey;
import com.survey.SurveyApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public String getString(String name) {
        return userDAO.getString(name);
    }

    @Transactional
    @Override
    public void save(User user) {
        userDAO.save(user);
    }
    @Transactional
    @Override
    public void delete(int id) {

    }

    @Transactional
    @Override
    public String createSurvey(Survey survey, String name) {
        return surveyDAO.createSurvey(survey,name);
    }

    //open survey that he created
    @Transactional
    @Override
    public StringBuilder openSurvey(int id,String name) {
        return surveyDAO.openSurvey(id, name);
    }

    @Transactional
    @Override
    public  StringBuilder getOpenedSurveys() {
        return  surveyDAO.getOpenedSurveys();
    }

    @Override
    public StringBuilder getResponses(String name) {
        return userDAO.getResponses(name);
    }

    //close survey from opened ones
    @Override
    public String closeSurvey(int id,String name) {
        return surveyDAO.closeSurvey(id,name);
    }

    @Transactional
    @Override
    public StringBuilder getOneOpenedSurvey(int id) {
        return surveyDAO.getOneOpenedSurvey(id);
    }

    //@Transactional
    @Override
    public String addResponses(String name, int []responsesGivens) {
        return userDAO.addResponses(name, responsesGivens);
    }

}
