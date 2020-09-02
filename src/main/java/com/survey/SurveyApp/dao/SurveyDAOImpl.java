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
    public Survey createSurvey(Survey survey,String name) {
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
        return survey;
    }
    @Override
    public Survey openSurvey(int id,String name) {

        Session currentSession = entityManager.unwrap(Session.class);
        User user = userDAO.findByUsername(name);
        Survey survey = currentSession.get(Survey.class, id);
        //verific daca cel ce el cere l-a si creat
        if(survey.getCreatorId() != user.getUser_id()) {
             return null;
        }
        return survey;
    }

    @Override
    public Survey closeSurvey(int id,String name) {
        Session currentSession = entityManager.unwrap(Session.class);

        Transaction tx = null;
        try{
            tx = currentSession.beginTransaction();

            User user = userDAO.findByUsername(name);
            Survey survey =
                    (Survey) currentSession.get(Survey.class, id);
            if(survey.getCreatorId() != user.getUser_id())
                return null;
            survey.setOpened(false);
            currentSession.update(survey);
            tx.commit();
            return survey;
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            currentSession.close();
        }
        return null;
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
    public Survey getOneSurvey(String name,int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Survey surveyObj = currentSession.get(Survey.class, id);
        User user = userDAO.findByUsername(name);
        if(!surveyObj.isOpened()&&user.getUser_id()!=surveyObj.getCreatorId()) {

            return null;
        }
        return surveyObj;
    }

}


