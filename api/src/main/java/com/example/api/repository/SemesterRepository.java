package com.example.api.repository;

import com.example.api.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SemesterRepository extends JpaRepository<Semester, Long> {

    List<Semester> findByCourseId(Long courseId);
}
