package com.example.studentsystem.repositories;

import com.example.studentsystem.dto.CourseDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<CourseDto, Integer> {
        @Query("SELECT c FROM CourseDto c WHERE c.name=:name")
        CourseDto getByName(String name);
}
