package com.example.api.repository;

import com.example.api.entity.Account;
import com.example.api.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByAccount(Account account);
}
