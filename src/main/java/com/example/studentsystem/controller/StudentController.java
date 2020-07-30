package com.example.studentsystem.controller;

import com.example.studentsystem.dto.StudentDto;
import com.example.studentsystem.services.CourseService;
import com.example.studentsystem.services.EnrollmentService;
import com.example.studentsystem.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Controller
@Component
@RequestMapping(path = "/demo")
public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * Users/Students
     *
     * @param name
     * @param email
     * @return
     */
    @PostMapping(path = "/students")
    public @ResponseBody
    String addNewStudent(@RequestParam String name
            , @RequestParam String email) {
        StudentDto student = studentService.getByEmail(email);
        if (student == null) {
            student = new StudentDto();
            student.setName(name);
            student.setEmail(email);
            student.setHash(sha256(email));
            try {
                studentService.save(student);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Student Saved";
        } else {
            return "Student exists";
        }

    }

    @GetMapping(path = "/students")
    public @ResponseBody
    Iterable<StudentDto> getAllUsers() {
        return studentService.getAll();
    }

    private String sha256(String email) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = md.digest(email.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

}
