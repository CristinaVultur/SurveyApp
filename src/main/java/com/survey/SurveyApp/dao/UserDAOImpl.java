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
    public List<User> get() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> query = currentSession.createQuery("from User ", User.class);
        List<User> list = query.getResultList();
        return list;
    }

    @Override
    public User get(String name) {
       // Session currentSession = entityManager.unwrap(Session.class);
        User userObj = this.findByUsername(name);
        return userObj;
    }

    @Override
    public List<Survey> getSurveys(String name) {
        Session currentSession = entityManager.unwrap(Session.class);
        User user = this.get(name);


        Query<Survey> query = currentSession.createQuery("from Survey where opened = true ", Survey.class);
        List<Survey> list = query.getResultList();
        for(Survey s:user.getSurveys()){
            if(!s.isOpened())
                list.add(s);
        }

        return list;


    }


    @Override
    public User findByUsername(String username) {
        List<User> users = this.get();

        for (User c : users) {
            if (c.getUsername().equals(username))
                return c;
        }
        return null;

    }

    @Override
    public void save(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(user);

    }

    @Override
    public Set<Response> getResponses(String name, int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        User user = this.findByUsername(name);
        Set<Response> responses = user.getResponses();
        for(Response r: responses){
            if(r.getQuestion().getSurvey().getSurvey_id()!=id)
                responses.remove(r);
        }
        return responses;




    }

    //raspunde la un survey
    @Override
    public Set<Response> addResponses(String name, int[] responsesGiven) {
        Session currentSession = entityManager.unwrap(Session.class);
        Transaction tx = null;
        try {
            tx = currentSession.beginTransaction();
            User userObj = this.findByUsername(name);
            Response responseObj = (Response) currentSession.get(Response.class, responsesGiven[0]);
            Survey survey = (Survey)  currentSession.get(Survey.class, responseObj.viewQuestion().viewSurvey().getSurvey_id());
            //serch for responses after id:
            int i=0;
            for(Question q : survey.getQuestions()){
                if(q.isRequired() && i >= responsesGiven.length) {
                return null;
                    }
                responseObj = (Response) currentSession.get(Response.class, responsesGiven[i]);
                //check if the response belongs to the question
                if(q.getId() != responseObj.viewQuestion().getId()){
                    //if it doesn't
                    //check if the questionwas mandatory
                    if(q.isRequired())
                        return null;
                    else continue;
                }
                responseObj.addRespondent(userObj);
                currentSession.saveOrUpdate(responseObj);
                userObj.getResponses().add(responseObj);
                currentSession.update(responseObj);
                i++;
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
