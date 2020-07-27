package com.example.studentsystem.servicesImpl;

import com.example.studentsystem.dao.Dao;
import com.example.studentsystem.dto.StudentDto;
import com.example.studentsystem.repositories.StudentRepository;
import com.example.studentsystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class StudentServiceImpl implements StudentService {
    @Autowired
    private Dao<StudentDto> studentDao;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Optional<StudentDto> getById(int id) {
        return studentDao.getById(id);
    }

    @Override
    public StudentDto getByEmail(String email) {
        return studentRepository.getByEmail(email);
    }

    @Override
    public StudentDto getByHash(String hash) {
        return studentRepository.getByHash(hash);
    }

    @Override
    public List<StudentDto> getAll() {
        return (List<StudentDto>) studentDao.getAll();
    }

    @Override
    public void save(StudentDto studentDto) throws Exception {
        studentDao.save(studentDto);
    }

}
