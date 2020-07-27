package com.example.studentsystem.servicesImpl;

import com.example.studentsystem.dao.Dao;
import com.example.studentsystem.dto.EnrollmentDto;
import com.example.studentsystem.repositories.EnrollmentRepository;
import com.example.studentsystem.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    private Dao<EnrollmentDto> enrollmentDao;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public Optional<EnrollmentDto> getById(int id) {
        return enrollmentDao.getById(id);
    }

    @Override
    public EnrollmentDto getByStudentIdAndCourseId(int student_id, int course_id) {
        return  enrollmentRepository.getByStudentIdAndCourseId(student_id, course_id);
    }

    @Override
    public List<EnrollmentDto> getAll() {
        return (List<EnrollmentDto>) enrollmentDao.getAll();
    }

    @Override
    public void save(EnrollmentDto enrollmentDto) throws Exception {
        enrollmentDao.save(enrollmentDto);
    }

    @Override
    public void delete(EnrollmentDto enrollmentDto) {
        enrollmentDao.delete(enrollmentDto);
    }

}
