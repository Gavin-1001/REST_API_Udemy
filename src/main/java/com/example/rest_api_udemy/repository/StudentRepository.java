package com.example.rest_api_udemy.repository;


import com.example.rest_api_udemy.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFirstName(String firstName);

    List<Student> findByLastName(String lastName);

    List<Student> findByEmail(String email);

    //returns just one Student object
    Student findByFirstNameAndLastName(String firstName, String lastName);
    //jpa will create a query where it checks for the firstName AND the lastName

    List<Student> findByFirstNameOrLastName(String firstName, String lastName);

    //using List of Student because we will have more than one record returned
    List<Student> findByFirstNameIn(List<String> firstNames);

    List<Student> findByFirstNameLike(String firstName);

    @Query("From Student where firstName=:firstName and lastName=:lastName")
    Student getByFirstNameOrLastName(String lastName, String firstName);
    //@Query("From Student where firstName=:firstname and lastName=:lastName")
    //Student getByFirstNameOrLastName(String lastName,@Param("firstname") String firstName);
    //if you don't want to query using the name of the variable from the entity, you can use @Param annotation to insert the query to the method in the repo

    //whenever you are modifying in the database you need to add the annotations @Modifying and @Transactional
    @Modifying
    @Transactional
    @Query("Update Student set firstName =:firstName where id=:id")
    Integer updateFirstName(Long id, String firstName);

    //again need the 2 annotations because we are deleting from the db
    @Modifying
    @Transactional
    @Query("Delete From Student where firstName=:firstName")
    Integer deleteByFirstName(String firstName);

    @Query("From Student where address.city = :city")
    List<Student> findByAddressCity(String city);

}
