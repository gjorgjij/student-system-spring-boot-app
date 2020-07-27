package com.example.studentsystem.controller;

import com.example.studentsystem.dto.CourseDto;
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
import java.util.List;

@Controller
@RequestMapping(path = "/demo")
public class MainController {
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
    @PostMapping(path = "/students/add")
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

    @GetMapping(path = "/students/all")
    public @ResponseBody
    Iterable<StudentDto> getAllUsers() {
        return studentService.getAll();
    }

    /**
     * Add new course
     *
     * @param name
     * @return
     */
    @PostMapping(path = "/courses/add")
    public @ResponseBody
    String addNewCourse(@RequestParam String name) {
        CourseDto course = courseService.getByName(name);
        if (course == null) {
            course = new CourseDto();
            course.setName(name);
            try {
                courseService.save(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Course successfully created!";
        } else {
            return "Course already exists";
        }
    }

    /**
     * Return all courses
     *
     * @return
     */
    @GetMapping(path = "/courses/all")
    public @ResponseBody
    Iterable<CourseDto> getAllCourses(@RequestHeader String hash) {
        if (authenticate(hash)) {
            return courseService.getAll();
        }
        return null;
    }

    /**
     * Return course by name
     *
     * @param name
     * @return
     */
    @GetMapping(path = "/courses/{name}")
    public @ResponseBody
    CourseDto getByName(@RequestHeader String hash, @PathVariable String name) {
        if (authenticate(hash)) {
            return courseService.getByName(name);
        }
        return null;
    }

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
        return null;
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
        return null;
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
        List<StudentDto> students = studentService.getAll();
        for (StudentDto student : students) {
            if (student.getHash().equals(hash)) {
                return true;
            }
        }
        return false;
    }
}

