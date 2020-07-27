package com.example.studentsystem.dao;

import com.example.studentsystem.dto.EnrollmentDto;
import com.example.studentsystem.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class EnrollmentDao implements Dao<EnrollmentDto> {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public Optional<EnrollmentDto> getById(int id) {
        return enrollmentRepository.findById(id);
    }

    @Override
    public Collection<EnrollmentDto> getAll() {
        return (Collection<EnrollmentDto>) enrollmentRepository.findAll();
    }

    @Override
    public void save(EnrollmentDto enrollmentDto) {
        enrollmentRepository.save(enrollmentDto);
    }

    @Override
    public void update(EnrollmentDto enrollmentDto) {
        enrollmentRepository.save(enrollmentDto);
    }

    @Override
    public void delete(EnrollmentDto enrollmentDto) {
        enrollmentRepository.delete(enrollmentDto);
    }
}
