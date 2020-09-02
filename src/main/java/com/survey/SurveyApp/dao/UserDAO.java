package com.survey.SurveyApp.dao;

import com.survey.SurveyApp.model.User;

import java.util.List;

public interface UserDAO {
    List<User> get();

    User get(String name);

    String getString(String name);

    User findByUsername(String username);

    void save(User user);
    StringBuilder getResponses(String name);

    String addResponses(String name, int []responsesGiven);
}
