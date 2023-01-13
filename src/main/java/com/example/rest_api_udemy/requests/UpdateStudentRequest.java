package com.example.rest_api_udemy.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateStudentRequest {

    //model class for updating api
    @NotNull(message = "Student Id is required")
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
