package com.example.rest_api_udemy.requests;

import lombok.Data;

@Data
public class CreateSubjectRequest {

    private String subjectName;

    private Double marksObtained;

}
