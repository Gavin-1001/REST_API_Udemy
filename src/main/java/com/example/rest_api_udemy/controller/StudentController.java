package com.example.rest_api_udemy.controller;


import com.example.rest_api_udemy.entity.Student;
import com.example.rest_api_udemy.requests.CreateStudentRequest;
import com.example.rest_api_udemy.requests.InQueryRequest;
import com.example.rest_api_udemy.requests.UpdateStudentRequest;
import com.example.rest_api_udemy.response.StudentResponse;
import com.example.rest_api_udemy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RESTCONTROLLER = combination of @Controller and @ResponseBody annotations. Otherwise, we would need to put a @Controller on the top of the class
//and @RepsonseBody on each method/api that we develop.

@RequestMapping("/api/student")
public class StudentController {


    @Autowired
    StudentService studentService;

    @GetMapping("/getAll")
    public List<StudentResponse> getAllStudents(){
        List<Student> studentList = studentService.getAllStudent();
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
    }


    @PostMapping("/create") //the createStudentRequest converts the json payload in the class
    //@Valid - All fields must be valid, or when the request is sent to the api it will return an error. User must insert all data!.
    public StudentResponse createStudent(@Valid @RequestBody CreateStudentRequest createStudentRequest){
        Student student = studentService.createStudent(createStudentRequest);

        return new StudentResponse(student);
    }

    //http://localhost:8080/api/student/update
    //the id needs to be passed in the json payload
    @PutMapping("/update")
    public StudentResponse updateStudent(@Valid @RequestBody UpdateStudentRequest updateStudentRequest){
        Student student = studentService.updateStudent(updateStudentRequest);
        return new StudentResponse(student);
    }


    /*
    @DeleteMapping("/delete")
    //the @requestParam annotation will take the id from the request {url /delete/?id=1} and assign the var id to the int in the url
    public String deleteStudent(@RequestParam long id){
        return studentService.deleteStudent(id);
    }*/

    //You can choose which method to use to delete a record in the db
    //PathVariable way
    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable long id){
        return studentService.deleteStudent(id);
    }

    @GetMapping("/getByFirstName/{firstName}")
    public List<StudentResponse> getByFirstName(@PathVariable String firstName) {
        //The return type will be a list of studentResponse, so this is the model class we have to retrieve the information for the student. Will return a list of student
        List<Student> studentList = studentService.getByFirstName(firstName);

        //THIS CODE JUST CONVERTS LIST OF STUDENT TO LIST OF STUDENTRESPONSE. IT IS A SAFETY CLASS THAT IS USED SO YOU DO NOT DIRECTLY EXPOSE YOUR ENTITY CLASS
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
        ///////////////////////////
    }

    @GetMapping("/getByLastName/{lastName}")
    public List<StudentResponse> getByLastName(@PathVariable String lastName){
        List<Student> studentList = studentService.getByLastName(lastName);

        //THIS CODE JUST CONVERTS LIST OF STUDENT TO LIST OF STUDENTRESPONSE. IT IS A SAFETY CLASS THAT IS USED SO YOU DO NOT DIRECTLY EXPOSE YOUR ENTITY CLASS
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
    }

    @GetMapping("/getByEmail/{email}")
    public List<StudentResponse> getByEmail(@PathVariable String email){
        List<Student> studentList = studentService.getByEmail(email);

        //THIS CODE JUST CONVERTS LIST OF STUDENT TO LIST OF STUDENTRESPONSE. IT IS A SAFETY CLASS THAT IS USED SO YOU DO NOT DIRECTLY EXPOSE YOUR ENTITY CLASS
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
    }



    @GetMapping("/getByFirstNameAndLastName/{firstName}/{lastName}")
    public StudentResponse getByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName){
        return new StudentResponse(studentService.getByFirstNameAndLastName(firstName, lastName));
        //Hibernate: select s1_0._id,s1_0._email,s1_0.first_name,s1_0.last_name from student_table s1_0 where s1_0.first_name=? and s1_0.last_name=?
        //This is the query statement that Spring queries in the db. Notice the AND, it matches the and in the repository
    }


    @GetMapping("/getByFirstNameOrLastName/{firstName}/{lastName}")
    public List<StudentResponse> getByFirstNameOrLastName(@PathVariable String firstName, @PathVariable String lastName){
        List<Student> studentList = studentService.getByFirstNameOrLastName(firstName, lastName);

        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
    }

    @GetMapping("/getByFirstNameIn")            //@RequestBody - whatever json that is passed into the request payload, it will be converted into the
    //object of this model class. When it has been converted, we will get a list of firstNames
    public List<StudentResponse> getByFirstNameIn(@RequestBody InQueryRequest inQueryRequest){
        List<Student> studentList = studentService.getByFirstNameIn(inQueryRequest);

        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
    }

    @GetMapping("/getAllWithPagination")
    // http://localhost:8080/api/student/getAllWithPagination?pageNo=1&pageSize=10
    public List<StudentResponse> getAllStudentsWithPagination(@RequestParam int pageNo, @RequestParam int pageSize){
        List<Student> studentList = studentService.getAllStudentsWithPagination(pageNo, pageSize);

        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
    }

    @GetMapping("/getAllWithSorting")
    // http://localhost:8080/api/student/getAllWithSorting
    public List<StudentResponse> getAllStudentsWithSorting(){
        //Lecture 45
        List<Student> studentList = studentService.getAllStudentsWithSorting();

        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
    }

    @GetMapping("/like/{firstName}")
    public List<StudentResponse> like(@PathVariable String firstName){
        List<Student> studentList = studentService.like(firstName);

        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
    }

    @PutMapping("/updateFirstName/{id}/{firstName}")
    public String updateStudentWithJpql(@PathVariable Long id, @PathVariable String firstName){
        return studentService.updateStudentWithJpql(id, firstName)+ "Student(s) updated";
    }

    @DeleteMapping("/deleteByFirstName/{firstName}")
    public String deleteStudent(@PathVariable String firstName){
        return studentService.deleteByFirstName(firstName) + "Student(s) deleted";
    }

    @GetMapping("/getByCity/{city}")
    //http://localhost:8080/api/student/getByCity/Dublin
    public List<StudentResponse> getByCity(@PathVariable String city) {
        List<Student> studentList = studentService.getByCity(city);

        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });

        return studentResponseList;
    }




}

