package com.survey.SurveyApp.dao;

import com.survey.SurveyApp.model.Question;
import com.survey.SurveyApp.model.Response;
import com.survey.SurveyApp.model.User;
import com.survey.SurveyApp.model.Survey;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;


@Repository
public class SurveyDAOImpl implements SurveyDAO {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    UserDAO userDAO;

    @Override
    public String createSurvey(Survey survey,String name) {
        Session currentSession = entityManager.unwrap(Session.class);
        //Transaction tx = null;
        //try {
            //tx = currentSession.beginTransaction();
        User creator = userDAO.findByUsername(name);
        Set<Question> questions = survey.getQuestions();
        for (Question q : questions) {
            q.setSurvey(survey);
            Set<Response> responses = q.getResponses();
            for (Response r : responses) {
                    r.setQuestion(q);
            }
        }
            survey.setCreator(creator);
            currentSession.save(survey);
            //tx.commit();
       // }catch (HibernateException e) {
            //if (tx!=null) tx.rollback();
            //e.printStackTrace();
        //}finally {
            //currentSession.close();
       // }
        return "Survey Created";
    }
    @Override
    public StringBuilder openSurvey(int id,String name) {

        Session currentSession = entityManager.unwrap(Session.class);
        User user = userDAO.findByUsername(name);
        Survey survey = currentSession.get(Survey.class, id);
        //verific daca cel ce el cere l-a si creat
        if(survey.getCreatorId() != user.getUser_id()) {
             StringBuilder ret = new StringBuilder("You are not allowed here");
             return ret;
        }
        return survey.showAnswers();
    }

    @Override
    public String closeSurvey(int id,String name) {
        Session currentSession = entityManager.unwrap(Session.class);

        Transaction tx = null;
        try{
            tx = currentSession.beginTransaction();

            User user = userDAO.findByUsername(name);
            Survey survey =
                    (Survey) currentSession.get(Survey.class, id);
            if(survey.getCreatorId() != user.getUser_id())
                return "You are not allowed to close this survey";
            survey.setOpened(false);
            currentSession.update(survey);
            tx.commit();
            return "Survey closed successfully";
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            currentSession.close();
        }
        return "idk";
    }

    @Override
    public StringBuilder getOpenedSurveys() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Survey> query = currentSession.createQuery("from Survey where opened = true ", Survey.class);
        List<Survey> list = query.getResultList();
        StringBuilder ret = new StringBuilder();
        for(Survey s: list){
            ret.append("\n"+s.showSurvey()+"\n");
        }
        return ret;
    }

    @Override
    public StringBuilder getOneOpenedSurvey(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Survey surveyObj = currentSession.get(Survey.class, id);
        if(!surveyObj.isOpened()) {
            StringBuilder not = new StringBuilder();
            not.append("Survey not opened");
            return not;
        }
        return surveyObj.showSurvey();
    }

}


