package com.example.api.repository;

import com.example.api.entity.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {

    List<Clazz> findByTeacherId(Long teacherId);


    @Query(value = "SELECT clazz_id FROM clazz_student WHERE student_id = :studentId", nativeQuery = true)
    Long getClazzIdByStudentId(Long studentId);

}
