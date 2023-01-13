package com.example.rest_api_udemy.service;


import com.example.rest_api_udemy.entity.Address;
import com.example.rest_api_udemy.entity.Student;
import com.example.rest_api_udemy.entity.Subject;
import com.example.rest_api_udemy.repository.AddressRepository;
import com.example.rest_api_udemy.repository.StudentRepository;
import com.example.rest_api_udemy.repository.SubjectRepository;
import com.example.rest_api_udemy.requests.CreateStudentRequest;
import com.example.rest_api_udemy.requests.CreateSubjectRequest;
import com.example.rest_api_udemy.requests.InQueryRequest;
import com.example.rest_api_udemy.requests.UpdateStudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SubjectRepository subjectRepository;


    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

/*    public Student createStudent(CreateStudentRequest createStudentRequest){
        Student student = new Student(createStudentRequest);
        student = studentRepository.save(student);
        return student;
        //Save - Whatevey entity class you provided, the object will be persisted in the db table. It will create a new record in the student table
    }*/

    public Student createStudent (CreateStudentRequest createStudentRequest) {
        Student student = new Student(createStudentRequest);

        Address address = new Address();
        address.setStreet(createStudentRequest.getStreet());
        address.setCity(createStudentRequest.getCity());

        address = addressRepository.save(address);

        student.setAddress(address);
        student = studentRepository.save(student);

        List<Subject> subjectsList = new ArrayList<Subject>();


        if(createStudentRequest.getSubjectsLearning() != null){ //because this is a list we need to loop through the list
            for(CreateSubjectRequest createSubjectRequest : createStudentRequest.getSubjectsLearning()){
                Subject subject = new Subject();
                subject.setSubjectName(createSubjectRequest.getSubjectName());
                subject.setMarksObtained(createSubjectRequest.getMarksObtained());
                subject.setStudent(student); //setting up the foreign key in the subjects table

                subjectsList.add(subject);
            }

            subjectRepository.saveAll(subjectsList);

        }

        student.setLearningSubjects(subjectsList);

        return student; //returns the student object entity class back to the controller
    }

    public Student updateStudent(UpdateStudentRequest updateStudentRequest){
        Student student = studentRepository.findById(updateStudentRequest.getId()).get();
        if(updateStudentRequest.getFirstName()!= null && !updateStudentRequest.getFirstName().isEmpty()){
            student.setFirstName(updateStudentRequest.getFirstName());
        }
        if(updateStudentRequest.getLastName()!= null && !updateStudentRequest.getLastName().isEmpty()){
            student.setLastName(updateStudentRequest.getLastName());
        }
        if(updateStudentRequest.getEmail()!= null && !updateStudentRequest.getEmail().isEmpty()){
            student.setEmail(updateStudentRequest.getEmail());
        }

        student = studentRepository.save(student);
        return student;
    }

    /* path for query reference http://localhost:8080/api/student/delete?id=2
    public String deleteStudent(long id){
        studentRepository.deleteById(id);
        return "Student has been deleted successfully";
    }
    */

    public String deleteStudent(long id){
        studentRepository.deleteById(id);
        return "Student has been deleted successfully";
    }



    public List<Student> getByFirstName(String firstName){
        //We are using a List because if there is for example more than one record where the record has the same firstName
        //this returns a list of student

        //call the repository
        return studentRepository.findByFirstName(firstName);
    }

    public List<Student> getByLastName(String lastName){
        return studentRepository.findByLastName(lastName);
    }

    public List<Student> getByEmail(String email){
        return studentRepository.findByEmail(email);
    }

    public Student getByFirstNameAndLastName(String firstName, String lastName){
        //return studentRepository.findByFirstNameAndLastName(firstName, lastName);
        return studentRepository.getByFirstNameOrLastName(lastName, firstName);
    }

    public List<Student> getByFirstNameOrLastName(String firstName, String lastName){

        //we need a list of student because the query contains an OR which will return more than one record. E.g. if a record has a firstName of John and another record
        //has a lastName containing Paul. It will return 2 seperate students
        return studentRepository.findByFirstNameOrLastName(firstName, lastName);
    }

    public List<Student> getByFirstNameIn(InQueryRequest inQueryRequest){
        return studentRepository.findByFirstNameIn(inQueryRequest.getFirstNames());
    }


    public List<Student> getAllStudentsWithPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        //The "-1" - The reason it is -1, because the PageRequest.of is an array and the index starts at 0th index.
        return studentRepository.findAll(pageable).getContent(); // lecture 44

    }

    public List<Student> getAllStudentsWithSorting() {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName", "lastName", "email");
        //The Sort.Direction.ASC can be sorted based on condition. I.E. sorted by firstName, or lastName or email
        return studentRepository.findAll(sort);
    }

    public List<Student> like(String firstName){
        return studentRepository.findByFirstNameLike(firstName);
    }

    public Integer updateStudentWithJpql(Long id, String firstName) {
        return studentRepository.updateFirstName(id, firstName);
    }

    public Integer deleteByFirstName(String firstName){
        return studentRepository.deleteByFirstName(firstName);
    }

    public List<Student> getByCity(String city) {
        return studentRepository.findByAddressCity(city);
    }
}
