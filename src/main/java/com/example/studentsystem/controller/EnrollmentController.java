package com.example.studentsystem.controller;

import com.example.studentsystem.dto.EnrollmentDto;
import com.example.studentsystem.dto.StudentDto;
import com.example.studentsystem.services.CourseService;
import com.example.studentsystem.services.EnrollmentService;
import com.example.studentsystem.services.StudentService;
import javassist.NotFoundException;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@Controller
@RequestMapping(path = "/enrollments")
public class EnrollmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentController.class);

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<String> addNewEnrollment(@RequestHeader String hash, @Valid @RequestBody EnrollmentDto enrollmentDto) {

        if (authenticate(hash)) {
            EnrollmentDto enrollment = enrollmentService.getByStudentIdAndCourseId(enrollmentDto.getStudent_id(), enrollmentDto.getCourse_id());
            if (enrollment != null) {
                return new ResponseEntity<String>("Student {" + enrollmentDto.getStudent_id() + "} already enrolled", HttpStatus.CONFLICT);
            }
            try {
                Integer enrollmentId = enrollmentService.save(enrollmentDto);
                return new ResponseEntity<String>(enrollmentId.toString(), HttpStatus.CREATED);
            } catch (Exception e) {
                LOGGER.error("Error found: {}", e.getMessage(), e);
                return new ResponseEntity<String>("Failed saving", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping(path = "/cancel")
    public ResponseEntity<String> cancelEnrollment(@RequestHeader String hash, @RequestBody EnrollmentDto enrollmentDto) {
        if (authenticate(hash)) {
            EnrollmentDto enrollment = enrollmentService.getByStudentIdAndCourseId(enrollmentDto.getStudent_id(), enrollmentDto.getCourse_id());
            if (enrollment != null) {
                enrollmentService.delete(enrollment);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            LOGGER.info("Student with id: " + enrollmentDto.getStudent_id() + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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

    private Boolean authenticate(String hash) {
        StudentDto student = studentService.getByHash(hash);
        return student != null;
    }
}
