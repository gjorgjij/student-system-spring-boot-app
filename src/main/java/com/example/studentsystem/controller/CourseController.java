package com.example.studentsystem.controller;

import com.example.studentsystem.dto.CourseDto;
import com.example.studentsystem.dto.StudentDto;
import com.example.studentsystem.services.CourseService;
import com.example.studentsystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@Controller
@RequestMapping(path = "/courses")
public class CourseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/{name}")
    public ResponseEntity<String> getByName(@RequestHeader String hash, @Valid @PathVariable String name) {
        try {
            Integer courseId = courseService.getByName(name).getId();
            return new ResponseEntity<String>(courseId.toString(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error found: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<String> addNewCourse(@Valid @RequestBody CourseDto courseDto) {
        CourseDto course = courseService.getByName(courseDto.getName());
        if (course != null) {
            return new ResponseEntity<String>("Course already exists", HttpStatus.CONFLICT);
        }
        try {
            Integer courseId = courseService.save(courseDto);
            return new ResponseEntity<String>(courseId.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error found: {}", e.getMessage(), e);
            return new ResponseEntity<String>("Failed saving", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<Iterable<CourseDto>> getAllCourses() {
        try {
            return new ResponseEntity<Iterable<CourseDto>>(courseService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error found: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private Boolean authenticate(String hash) {
        StudentDto student = studentService.getByHash(hash);
        return student != null;
    }
}
