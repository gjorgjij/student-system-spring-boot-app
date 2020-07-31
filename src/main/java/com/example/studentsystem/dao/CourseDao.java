package com.example.studentsystem.dao;

import com.example.studentsystem.dto.CourseDto;
import com.example.studentsystem.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Optional;

@Component
public class CourseDao implements Dao<CourseDto> {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Optional<CourseDto> getById(int id) {
        return courseRepository.findById(id);
    }

    @Override
    public Collection<CourseDto> getAll() {
        return (Collection<CourseDto>) courseRepository.findAll();
    }

    @Override
    public CourseDto save(CourseDto courseDto) {
        return courseRepository.save(courseDto);
    }

    @Override
    public void update(CourseDto courseDto) {
        courseRepository.save(courseDto);
    }

    @Override
    public void delete(CourseDto courseDto) {
        courseRepository.delete(courseDto);
    }
}
