package com.example.studentsystem.repositories;

import com.example.studentsystem.dto.StudentDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<StudentDto, Integer> {
    @Query("SELECT s FROM StudentDto s WHERE s.email=:email")
    StudentDto getByEmail(String email);
}
