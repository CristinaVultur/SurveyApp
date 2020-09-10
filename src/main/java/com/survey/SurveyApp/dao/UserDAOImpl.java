package com.survey.SurveyApp.dao;

import com.survey.SurveyApp.Dto.ResponseDto;
import com.survey.SurveyApp.model.Question;
import com.survey.SurveyApp.model.Response;
import com.survey.SurveyApp.model.Survey;
import com.survey.SurveyApp.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;


    @Override
    public User get(String name) {
        User userObj = this.findByUsername(name);
        return userObj;
    }

    //arata chestionarele ce sunt deschise sau create de user
    @Override
    public List<Survey> getSurveys(String name) {
        Session currentSession = entityManager.unwrap(Session.class);
        User user = this.get(name);

        Query<Survey> query = currentSession.createQuery("from Survey where opened = true ", Survey.class);
        List<Survey> list = query.getResultList();
        for(Survey s:user.getSurveys()){
            if(!s.isOpened())//daca era deschis deja se afla in lista de chestionare
                list.add(s);
        }

        return list;


    }

    //gaseste un user dupa username
    @Override
    public User findByUsername(String username) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> query = currentSession.createQuery("from User ", User.class);
        List<User> users = query.getResultList();

        for (User c : users) {
            if (c.getUsername().equals(username))
                return c;
        }

        return null;

    }

    //get the responses of a user to a survey
    @Override
    public Set<Response> getResponses(String name, int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        User user = this.findByUsername(name);
        Set<Response> responses = user.getResponses();//toate raspunsurile userului la toate chestionarele
        Set<Response> returned = new HashSet<>();
        for(Response r: responses){
            if(r.getQuestion().getSurvey().getSurvey_id()==id)//ce raspuns apartine de survey-ul dorit, adaug la lista
                returned.add(r);
        }
        return returned;
    }

    //raspunde la un survey
    @Override
    public Set<Response> addResponses(String name, int[] responsesGiven,int survey_id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            User userObj = this.findByUsername(name);
            Survey survey = (Survey) currentSession.get(Survey.class, survey_id);
            int i=0;
            for(Question q : survey.getQuestions()){
                if(responsesGiven[i] == 0)
                    if(q.isRequired())//u need to give an answer
                        return userObj.getResponses();
                 i++;
            }
            for(i = 0; i < responsesGiven.length;i++){
                if(responsesGiven[i] == 0){
                        continue;//continue to the next answer
                    }
                else{//add the response
                    Response responseObj = (Response) currentSession.get(Response.class, responsesGiven[i]);
                    responseObj.addRespondent(userObj);
                    currentSession.saveOrUpdate(responseObj);
                    userObj.getResponses().add(responseObj);
                    currentSession.update(responseObj);
                }
            }
            currentSession.update(userObj);
            tx.commit();
            return userObj.getResponses();

        }catch (
    HibernateException e) {
        if (tx!=null) tx.rollback();
        e.printStackTrace();
    }finally {
        currentSession.close();
    }
        return null;
    }

}
