package com.survey.SurveyApp.Dto;

public class ResponseDto {

    private String text;

    private QuestionsDto question;

    public QuestionsDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionsDto question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
