package com.example.api.repository;

import com.example.api.entity.Account;
import com.example.api.entity.Student;
import com.example.api.payload.response.StudentResponseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentCode(String studentCode);

    List<Student> findAllByCourseId(Long courseId);

    Optional<Student> findByAccount(Account account);




}
