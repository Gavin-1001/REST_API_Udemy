package com.example.rest_api_udemy.response;

import com.example.rest_api_udemy.entity.Subject;
import lombok.Data;

@Data
public class SubjectResponse {
    //model class for SubjectResponse
    private Long id;

    private String subjectName;

    private Double marksObtained;

    public SubjectResponse(Subject subject){
        this.id = subject.getId();
        this.subjectName = subject.getSubjectName();
        this.marksObtained = subject.getMarksObtained();
    }

}
