package com.example.studentsystem.services;

import com.example.studentsystem.dto.CourseDto;
import com.example.studentsystem.dto.EnrollmentDto;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Optional<CourseDto> getById(int id);

    CourseDto getByName(String name);

    List<CourseDto> getAll();

    void save(CourseDto courseDto) throws Exception;

    List<EnrollmentDto> fetchStudentsByCourseName(String name);

}
