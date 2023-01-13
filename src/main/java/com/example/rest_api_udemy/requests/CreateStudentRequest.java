package com.example.rest_api_udemy.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CreateStudentRequest {
    //private String id;

    @JsonProperty("first_name")
    @NotBlank(message = "First name is required")
    //if the firstName value is not valid (i.e. It has no value). When sent the request will return am error
    private String firstName;

    private String lastName;

    private String email;

    private String street;

    private String city;

    private List<CreateSubjectRequest> subjectsLearning;
    //subjectsLearning is the json Key
    //
}
