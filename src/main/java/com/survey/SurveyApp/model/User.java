package com.survey.SurveyApp.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column
    private String name;
    @Column
    private String username;
    @Column
    private String password;

    @Column
    private boolean isAdmin;

    @OneToMany(mappedBy="creator", cascade = CascadeType.ALL)
    private Set<Survey> surveys;

    @ManyToMany(cascade = { CascadeType.ALL },fetch = FetchType.LAZY)
    @JoinTable(
            name = "responses_given",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "response_id") }
    )
    Set<Response> responses = new HashSet<>();

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Set<Survey> getSurveys() {
        return surveys;
    }

    public Set<Response> getResponses() {
        return responses;
    }

    public void setResponses(Set<Response> responses) {
        this.responses = responses;
    }

    public void setSurveys(Set<Survey> surveys) {
        this.surveys = surveys;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                "\n, surveys=" + surveys +
                "}\n";
    }

    public StringBuilder getStringResponses() {

        StringBuilder answers= new StringBuilder();
        answers.append("Responses given :\n");
            for(Response r : responses){
                answers.append("\n"+r.getText());
            }
            answers.append("\n");
        return answers;


    }
}
