package com.example.rest_api_udemy.response;

import com.example.rest_api_udemy.entity.Student;
import com.example.rest_api_udemy.entity.Subject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StudentResponse {

    //@JsonIgnore
    private long id;

    @JsonProperty("first_name")
    private String firstName;

    //    @JsonProperty("last_name")
    private String lastName;

    //    @JsonProperty("_email")
    private String email;

    private String fullName;
    //when GETting all student records /getAll it will return student firstName and lastName concatenated
    //when POSTing data the payload sent will contain the fullName variable in the sent data

    private String street;

    private String city;

    private List<SubjectResponse> learningSubjects;

    //converting entity class Student to model class StudentResponse
    public StudentResponse(Student student){
        //you don't want the id displayed in the json data
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.street = student.getAddress().getStreet();
        this.city = student.getAddress().getCity();
        //this.fullName = student.getFirstName() + " " + student.getLastName();

        //inside of the student entity class we have a list of subjects that we will convert to the SubjectResponse List, basically a list of out SubjectResponse Model class
        if(student.getLearningSubjects() != null){
            learningSubjects = new ArrayList<SubjectResponse>();
            for(Subject subject: student.getLearningSubjects()){
                learningSubjects.add( //so we are looping through the ArrayList and converting it
                        new SubjectResponse(subject)
                );
            }
        }
    }

}
