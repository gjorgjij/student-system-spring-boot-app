package com.example.studentsystem.dao;

import com.example.studentsystem.dto.StudentDto;
import com.example.studentsystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class StudentDao implements Dao<StudentDto> {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Optional<StudentDto> getById(int id) {
        return studentRepository.findById(id);
    }

    @Override
    public Collection<StudentDto> getAll() {
        return (Collection<StudentDto>) studentRepository.findAll();
    }

    @Override
    public StudentDto save(StudentDto studentDto) {
        return studentRepository.save(studentDto);
    }

    @Override
    public void update(StudentDto studentDto) {
        studentRepository.save(studentDto);
    }

    @Override
    public void delete(StudentDto studentDto) {
        studentRepository.delete(studentDto);
    }
}
