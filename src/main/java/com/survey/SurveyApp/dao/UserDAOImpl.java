package com.survey.SurveyApp.dao;

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
import java.util.List;

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
    public String getString(String name) {
        //Session currentSession = entityManager.unwrap(Session.class);
        User user = this.get(name);
        return user.toString();
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
    public StringBuilder getResponses(String name) {
        //Session currentSession = entityManager.unwrap(Session.class);
        User user = this.findByUsername(name);
        return user.getStringResponses();
    }

    //raspunde la un survey
    @Override
    public String addResponses(String name, int[] responsesGiven) {
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
                return "Mandatory question not answered properly";
                    }
                responseObj = (Response) currentSession.get(Response.class, responsesGiven[i]);
                //check if the response belongs to the question
                if(q.getId() != responseObj.viewQuestion().getId()){
                    //if it doesn't
                    //check if the questionwas mandatory
                    if(q.isRequired())
                        return "Mandatory question not answered properly";
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

        }catch (
    HibernateException e) {
        if (tx!=null) tx.rollback();
        e.printStackTrace();
    }finally {
        currentSession.close();
    }
        return "Done";
    }

}
