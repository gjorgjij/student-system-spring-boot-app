package com.example.studentsystem.services;

import com.example.studentsystem.dto.EnrollmentDto;
import com.example.studentsystem.dto.StudentDto;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Optional<StudentDto> getById(int id);

    StudentDto getByEmail(String email);

    StudentDto getByHash(String hash);

    List<StudentDto> getAll();

    void save(StudentDto studentDto) throws Exception;

}
