package com.example.api.repository;

import com.example.api.entity.Schedule;
import com.example.api.entity.Semester;
import com.example.api.payload.response.ScheduleCheckStartDate;
import com.example.api.payload.response.ScheduleWithClazzResponse;
import com.example.api.payload.response.StudentWithGradeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findBySemesterId(Long semesterId);

    @Query(value = " SELECT sc.schedule_id AS scheduleId,sc.name AS name,s.name AS semesterName," +
            " sc.`status` AS status, sc.start_date AS startDate, sc.end_date AS endDate, sc.number_of_sessions AS numberOfSessions FROM schedule sc JOIN semester s ON \n" +
            " sc.semester_id = s.semester_id \n" +
            " JOIN course c ON c.course_id = s.course_id JOIN clazz cl \n" +
            " ON cl.course_id = s.course_id \n" +
            " WHERE cl.clazz_id = :clazzId", nativeQuery = true)
    List<ScheduleWithClazzResponse> findScheduleByClazzId(@Param("clazzId") Long clazzId);

    Optional<Schedule> findBySemesterAndId(Semester semester, Long id);
    Optional<Schedule> findById(Long id);

    @Query(value = "SELECT startDate, scheduleId, clazz_code\n" +
            "FROM (\n" +
            "    SELECT \n" +
            "        cl.start_date AS startDate,\n" +
            "        sch.schedule_id AS scheduleId,\n" +
            "        cl.clazz_code,\n" +
            "        ROW_NUMBER() OVER(PARTITION BY cl.clazz_code ORDER BY sch.schedule_id) AS rn\n" +
            "    FROM \n" +
            "        clazz cl\n" +
            "    JOIN \n" +
            "        course c ON cl.course_id = c.course_id\n" +
            "    JOIN \n" +
            "        semester s ON s.course_id = c.course_id\n" +
            "    JOIN \n" +
            "        `schedule` sch ON sch.semester_id = s.semester_id\n" +
            "    WHERE \n" +
            "        cl.start_date = CURDATE()\n" +
            ") AS subquery\n" +
            "WHERE rn = 1", nativeQuery = true)
    List<ScheduleCheckStartDate> getAllCheckStartDate();

}
