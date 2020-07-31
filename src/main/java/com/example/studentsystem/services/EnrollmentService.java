package com.example.studentsystem.services;

import com.example.studentsystem.dto.EnrollmentDto;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {

    Optional<EnrollmentDto> getById(int id);

    EnrollmentDto getByStudentIdAndCourseId(int student_id, int course_id);

    List<EnrollmentDto> getAll();

    Integer save(EnrollmentDto enrollmentDto) throws Exception;

    void delete(EnrollmentDto enrollmentDto);
}
