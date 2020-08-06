package com.example.studentsystem.controller;

import com.example.studentsystem.config.JwtTokenUtil;
import com.example.studentsystem.dto.EnrollmentDto;
import com.example.studentsystem.services.EnrollmentService;
import com.example.studentsystem.services.StudentService;
import com.example.studentsystem.servicesImpl.JwtUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;


@Component
@Controller
@RequestMapping(path = "/enrollments")
public class EnrollmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentController.class);

    @Autowired
    private EnrollmentService enrollmentService;

    @RolesAllowed("ADMIN")
    @PostMapping
    public ResponseEntity<String> addNewEnrollment(@Valid @RequestBody EnrollmentDto enrollmentDto) {
        EnrollmentDto enrollment = enrollmentService.getByStudentIdAndCourseId(enrollmentDto.getStudentId(), enrollmentDto.getCourseId());
        if (enrollment != null) {
            return new ResponseEntity<String>("Student {" + enrollmentDto.getStudentId() + "} already enrolled", HttpStatus.CONFLICT);
        }
        try {
            Integer enrollmentId = enrollmentService.save(enrollmentDto);
            return new ResponseEntity<String>(enrollmentId.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error found: {}", e.getMessage(), e);
            return new ResponseEntity<String>("Failed saving", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RolesAllowed("REMOVER")
    @PostMapping(path = "/cancel")
    public ResponseEntity<String> cancelEnrollment(@RequestBody EnrollmentDto enrollmentDto) {
        EnrollmentDto enrollment = enrollmentService.getByStudentIdAndCourseId(enrollmentDto.getStudentId(), enrollmentDto.getCourseId());
        if (enrollment != null) {
            enrollmentService.delete(enrollment);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        LOGGER.info("Student with id: " + enrollmentDto.getStudentId() + " not found.");

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<Iterable<EnrollmentDto>> getAllEnrollments() {
        try {
            return new ResponseEntity<>(enrollmentService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error found: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
