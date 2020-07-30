package com.example.studentsystem.controller;

import com.example.studentsystem.dto.EnrollmentDto;
import com.example.studentsystem.dto.StudentDto;
import com.example.studentsystem.services.CourseService;
import com.example.studentsystem.services.EnrollmentService;
import com.example.studentsystem.services.StudentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Controller
@RequestMapping(path = "/demo")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    /**
     * Add enrollment
     *
     * @param student_id
     * @param course_id
     * @return
     */
    @PostMapping(path = "/enrollments/add")
    public @ResponseBody
    String addNewEnrollment(@RequestHeader String hash, @RequestParam int student_id, @RequestParam int course_id) {

        EnrollmentDto enrollment = enrollmentService.getByStudentIdAndCourseId(student_id, course_id);

        if (authenticate(hash)) {
            if (enrollment == null) {
                enrollment = new EnrollmentDto();
                enrollment.setStudent_id(student_id);
                enrollment.setCourse_id(course_id);
                try {
                    enrollmentService.save(enrollment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Student with id:" + student_id + " successfully enrolled in course with id:" + course_id;

            } else {
                return "Student with id:" + student_id + " already enrolled in course with id:" + course_id;
            }
        }
        return "Permission not allowed";
    }

    /**
     * Cancel enrollment for specific student and course
     *
     * @param student_id
     * @param course_id
     * @return
     */
    @PostMapping(path = "/enrollments/cancel")
    public @ResponseBody
    String cancelEnrollment(@RequestHeader String hash, @RequestParam int student_id, @RequestParam int course_id) throws NotFoundException {

        EnrollmentDto enrollment = enrollmentService.getByStudentIdAndCourseId(student_id, course_id);
        if (authenticate(hash)) {
            if (enrollment == null) {
                return "Enrollment not found to be deleted";
            } else {
                enrollmentService.delete(enrollment);
                return "Successfully cancelled enrollment for student with id:" + student_id;
            }
        }
        return "Permission not allowed";
    }

    /**
     * Return all enrollments
     *
     * @return
     */
    @GetMapping(path = "/enrollments/all")
    public @ResponseBody
    Iterable<EnrollmentDto> getAllEnrollments() {
        return enrollmentService.getAll();
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
        if(student != null){
            return true;
        }
        return false;
    }
}
