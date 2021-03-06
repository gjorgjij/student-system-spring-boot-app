package com.example.studentsystem.controller;

import com.example.studentsystem.dto.StudentDto;
import com.example.studentsystem.services.StudentService;
import org.hibernate.DuplicateMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Component
@Controller
@RequestMapping(path = "/students")
public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<String> addNewStudent(@Valid @RequestBody StudentDto studentDto) throws DuplicateMappingException {
        StudentDto student = studentService.getByEmail(studentDto.getEmail());
        if (student != null) {
            return new ResponseEntity<String>("Created", HttpStatus.CREATED);
        }

        try {
            Integer studentId = studentService.save(studentDto);
            return new ResponseEntity<String>(studentId.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error found: {}", e.getMessage(), e);
            return new ResponseEntity<String>("Failed saving", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<Iterable<StudentDto>> getAllStudents() {
        try {
            return new ResponseEntity<>(studentService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error found: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
