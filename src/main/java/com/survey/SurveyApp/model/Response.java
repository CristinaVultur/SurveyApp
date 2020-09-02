package com.survey.SurveyApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "responses")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int response_id;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name ="question_id")
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public Set<User> getRespondents() {
        return respondents;
    }

    @ManyToMany(mappedBy = "responses")
    private Set<User> respondents = new HashSet<>();

    public int getResponse_id() {
        return response_id;
    }

    public void setResponse_id(int response_id) {
        this.response_id = response_id;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question viewQuestion(){
        return question;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addRespondent(User user){
        respondents.add(user);
    }

    @Override
    public String toString() {
        return "Response{" +
                "response_id=" + response_id +
                ", text='" + text + '\'' +
                "}\n";
    }
}
