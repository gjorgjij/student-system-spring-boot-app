package com.example.studentsystem.repositories;

import com.example.studentsystem.dto.EnrollmentDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EnrollmentRepository extends CrudRepository<EnrollmentDto, Integer> {
    @Query("SELECT e FROM EnrollmentDto e WHERE e.student_id=:student_id and e.course_id=:course_id")
    EnrollmentDto getByStudentIdAndCourseId(int student_id, int course_id);
}
