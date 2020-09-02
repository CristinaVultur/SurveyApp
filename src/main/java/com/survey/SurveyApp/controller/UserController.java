package com.survey.SurveyApp.controller;

import com.survey.SurveyApp.Dto.ResponseDto;
import com.survey.SurveyApp.Dto.SurveyDto;
import com.survey.SurveyApp.Dto.SurveyInverseDto;
import com.survey.SurveyApp.model.Response;
import com.survey.SurveyApp.model.User;
import com.survey.SurveyApp.model.Survey;
import com.survey.SurveyApp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    //show responses given to a survey

    @GetMapping("/survey/{id}/responses")
    public Set<ResponseDto> getResponses(Principal principal, @PathVariable int id){
        return convertResponsesToDo(userService.getResponses(principal.getName(), id));
    }
    //show open surveys for resp.
    //all open+created for coord
    @GetMapping("/survey")
    public List<SurveyDto> getSurveys(Principal principal){
        return convertSurveysToDo(userService.getSurveys(principal.getName()));
    }

    //open a survey created by a user
    //with the responses given by other users
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/survey/responses/{survey_id}")
    public SurveyInverseDto openSurvey(Principal principal, @PathVariable int survey_id){
        return convertSurveyInverseToDo(userService.openSurvey(survey_id,principal.getName()));
    }

    //close a survey
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/survey")
    public Survey closeSurvey(Principal principal, @RequestBody int survey_id){
        return userService.closeSurvey(survey_id,principal.getName());
    }

    //show one survey
    @GetMapping("/survey/{id}")
    public Survey getOneSurvey(Principal principal,@PathVariable int id){
        return userService.getOneSurvey(principal.getName(),id);
    }


    //respond to a survey
    @PostMapping("/survey/{survey_id}")
    public Set<ResponseDto> respondToSurvey(Principal principal, @PathVariable int survey_id, @RequestBody int []responsesGiven){
        return convertResponsesToDo(userService.addResponses(principal.getName(), responsesGiven));
    }

    //create new survey
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/survey")
    public Survey createSurvey(Principal principal,@RequestBody Survey surveyObj){
        return userService.createSurvey(surveyObj,principal.getName());
    }


    private SurveyDto convertSurveyToDo(Survey survey) {
        SurveyDto surveyDto = modelMapper.map(survey, SurveyDto.class);
        return surveyDto;
    }

    private List<SurveyDto> convertSurveysToDo(List<Survey> surveys) {

        List<SurveyDto> surveyDtos = new ArrayList();
        for(Survey s:surveys){
            surveyDtos.add(convertSurveyToDo(s));
        }
        return surveyDtos;
    }
    private SurveyInverseDto convertSurveyInverseToDo(Survey survey) {
        SurveyInverseDto surveyDto = modelMapper.map(survey, SurveyInverseDto.class);
        return surveyDto;
    }

    private ResponseDto convertResponseToDo(Response response) {
        ResponseDto responseDto = modelMapper.map(response, ResponseDto.class);
        return responseDto;
    }

    private Set<ResponseDto> convertResponsesToDo(Set <Response> responses) {

        Set <ResponseDto> responseDtos= new HashSet<>();
        for(Response r: responses){
            responseDtos.add(convertResponseToDo(r));
        }
        return responseDtos;
    }
}