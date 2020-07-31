package com.example.studentsystem.repositories;

import com.example.studentsystem.dto.EnrollmentDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EnrollmentRepository extends CrudRepository<EnrollmentDto, Integer> {
    @Query("SELECT e FROM EnrollmentDto e WHERE e.studentId=:student_id and e.courseId=:course_id")
    EnrollmentDto getByStudentIdAndCourseId(int student_id, int course_id);
}
