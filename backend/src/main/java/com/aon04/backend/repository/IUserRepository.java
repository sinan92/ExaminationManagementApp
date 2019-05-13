package com.aon04.backend.repository;

import com.aon04.backend.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u LEFT JOIN u.finishedExam f ON u.id = f.user.id")
    List<User> findAllWithExams();

    @Query("SELECT u FROM User u WHERE u.role = 1")
    List<User> findAllStudents();

    @Query(value = "SELECT * FROM users u WHERE u.student_number = :studentNumber LIMIT 1", nativeQuery = true)
    User findUserByStudentNumber(@Param("studentNumber") String studentNumber);

    @Query(value = "SELECT * FROM users u WHERE u.first_name = :FirstName AND u.last_name = :LastName LIMIT 1", nativeQuery = true)
    User findUserByName(@Param("FirstName") String firstName, @Param("LastName") String lastName);
}
