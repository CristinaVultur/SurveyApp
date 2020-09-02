package com.survey.SurveyApp.controller;

import com.survey.SurveyApp.model.User;
import com.survey.SurveyApp.model.Survey;
import com.survey.SurveyApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public  String home(){
        return ("<h1>Welcome</h1>");
    }

    //show all the surveys he responded too
    //with the answers

    @GetMapping("/responses")
    public StringBuilder getResponses(Principal principal){
        return userService.getResponses(principal.getName());
    }
    //show all the surveys he created
    //with the answers

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/created")
    public String getCreatedSurveys(Principal principal){
        return userService.getString(principal.getName());
    }

    //open a survey created by a user

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/created/{survey_id}")
    public StringBuilder openSurvey(Principal principal, @PathVariable int survey_id){
        return userService.openSurvey(survey_id,principal.getName());
    }

    //close a survey
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/close")
    public String closeSurvey(Principal principal, @RequestBody int survey_id){
        return userService.closeSurvey(survey_id,principal.getName());
    }

    //show opened surveys
    @GetMapping("/surveys")
    public StringBuilder getOpenedSurveys(){
        return userService.getOpenedSurveys();
    }

    //show one opened survey
    @GetMapping("/surveys/{id}")
    public StringBuilder getOneOpenedSurvey(@PathVariable int id){
        return userService.getOneOpenedSurvey(id);
    }


    //respond to a survey
    @PostMapping("/respond/{survey_id}")
    public String respondToSurvey(Principal principal, @PathVariable int survey_id, @RequestBody int []responsesGiven){
        return userService.addResponses(principal.getName(), responsesGiven);
    }

    //create new survey
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/createSurvey")
    public String createSurvey(Principal principal,@RequestBody Survey surveyObj){
        return userService.createSurvey(surveyObj,principal.getName());
    }

}
