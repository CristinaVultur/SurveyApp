package com.survey.SurveyApp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question {

    //aici trb schimbat
   //
    // @EmbeddedId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int question_id;

   @Column
   private String text;

   @Column
   boolean required;

   @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="survey_id")
    private Survey survey;


    @OneToMany(mappedBy="question", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Response> responses;

    public int getId() {
        return question_id;
    }

    public void setId(int id) {
        this.question_id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public Survey viewSurvey(){
        return survey;
    }
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public Set<Response> getResponses() {
        return responses;
    }

    public void setResponses(Set<Response> responses) {
        this.responses = responses;
    }

    //verify if a response belongs to the question
    public boolean belongs(int id){
        for(Response r:responses){
            if(r.getResponse_id() == id)
                return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                ", text='" + text + '\'' +
                ", required=" + required +
                ", responses=" + responses +
                '}';
    }
}
