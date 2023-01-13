package com.example.rest_api_udemy.repository;

import com.example.rest_api_udemy.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
