package com.survey.SurveyApp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "surveys")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int survey_id;

    @Column
    private String name;

    @Column
    private boolean opened;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User creator;

    @OneToMany(mappedBy="survey", cascade = CascadeType.ALL)
    private Set<Question> questions;

    public int getSurvey_id() {
        return survey_id;
    }


    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public int getCreatorId() {
        return creator.getUser_id();
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public User getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "survey_id=" + survey_id +
                ", name='" + name + '\'' +
                ", opened=" + opened +
                ", questions=" + questions +
                '}';
    }

    public StringBuilder showAnswers(){
        StringBuilder answers= new StringBuilder();
        answers.append("Survey: "+name+"\n");
                for(Question q: this.getQuestions()){
                    answers.append("Question :"+ q.getText()+"\n"+"Responses:");
                    for(Response r :q.getResponses()){
                        answers.append("\n"+r.getText()+" Users that responded with this: ");
                        for(User u : r.getRespondents()){
                            answers.append(u.getUsername()+" ");
                        }
                    }
                    answers.append("\n");
                }
                return answers;
    }
    public StringBuilder showSurvey(){

        StringBuilder answers= new StringBuilder();
        answers.append("Opened survey :\n");
        answers.append("Survey: "+name+"\n");
        for(Question q: this.getQuestions()){
            answers.append("Question :"+ q.getText()+"\n"+"Responses:");
            for(Response r :q.getResponses()){
                answers.append("\n"+r.getText());
            }
            answers.append("\n");
        }
        return answers;
    }
}
