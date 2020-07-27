package com.example.studentsystem.servicesImpl;

import com.example.studentsystem.dao.Dao;
import com.example.studentsystem.dto.CourseDto;
import com.example.studentsystem.dto.EnrollmentDto;
import com.example.studentsystem.repositories.CourseRepository;
import com.example.studentsystem.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseServiceImpl implements CourseService {
    @Autowired
    private Dao<CourseDto> courseDao;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Optional<CourseDto> getById(int id) {
        return courseDao.getById(id);
    }

    @Override
    public CourseDto getByName(String name) {
        return courseRepository.getByName(name);
    }

    @Override
    public List<CourseDto> getAll() {
        return (List<CourseDto>) courseDao.getAll();
    }

    @Override
    public void save(CourseDto courseDto) throws Exception {
        courseDao.save(courseDto);
    }

    @Override
    public List<EnrollmentDto> fetchStudentsByCourseName(String name) {
        return null;
    }
}
