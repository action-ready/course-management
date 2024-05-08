package com.example.api.repository;

import com.example.api.entity.Grades;
import com.example.api.payload.response.StudentWithGradeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradesRepository extends JpaRepository<Grades, Long> {

//    @Query(value = "SELECT s.student_id AS studentId, s.student_code AS studentCode, s.full_name AS fullName, COALESCE(g.grade, '') AS grade " +
//            "FROM student s " +
//            "LEFT JOIN grades g ON g.student_id = s.student_id " +
//            "AND g.clazz_id = :clazzId AND g.schedule_id = :scheduleId", nativeQuery = true)
//    List<StudentWithGradeDTO> findAllByClazzIdAndScheduleId(@Param("clazzId") Long clazzId,
//                                                            @Param("scheduleId") Long scheduleId);


    @Query(value = "SELECT g.grades_id AS gradesId,  s.student_id AS studentId, s.student_code AS studentCode, s.full_name AS fullName,\n" +
            " COALESCE(g.grade, '') AS grade \n" +
            "            FROM student s \n" +
            "           left JOIN clazz_student c ON s.student_id = c.student_id \n" +
            "            left JOIN grades g ON g.student_id = s.student_id AND g.schedule_id = :scheduleId \n" +
            "            WHERE c.clazz_id = :clazzId ", nativeQuery = true)
    List<StudentWithGradeDTO> findAllByClazzIdAndScheduleId(@Param("clazzId") Long clazzId,
                                                            @Param("scheduleId") Long scheduleId);


//    @Query(value = "SELECT st.student_id AS studentId, st.student_code AS studentCode, st.full_name AS fullName, COALESCE(g.grade, '') AS grade \n" +
//            "FROM student st left JOIN grades g ON g.student_id = st.student_id and schedule_id = :scheduleId \n" +
//            "WHERE g.clazz_id = (SELECT DISTINCT clazz_id FROM clazz_student WHERE clazz_id = :clazzId)"
//           , nativeQuery = true)
//    List<StudentWithGradeDTO> findAllByClazzIdAndScheduleId(@Param("clazzId") Long clazzId,
//                                                            @Param("scheduleId") Long scheduleId);


}
