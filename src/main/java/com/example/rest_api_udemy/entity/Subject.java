package com.example.rest_api_udemy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="subject_name")
    private String subjectName;

    @Column(name="marks_obtained")
    private Double marksObtained;

    @ManyToOne //many students can study one subject. Many subjects are studied by one student
    @JoinColumn(name="student_id")
    private Student student;

}
