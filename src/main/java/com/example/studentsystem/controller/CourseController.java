package com.example.studentsystem.controller;

import com.example.studentsystem.dto.CourseDto;
import com.example.studentsystem.dto.StudentDto;
import com.example.studentsystem.services.CourseService;
import com.example.studentsystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Controller
@RequestMapping(path = "/demo")
@Component
public class CourseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    /**
     * Return course by name
     *
     * @param name
     * @return
     */
    @GetMapping(path = "/courses/{name}")
    public @ResponseBody
    Object getByName(@RequestHeader String hash, @PathVariable String name) {
        if (authenticate(hash)) {
            return courseService.getByName(name);
        }
        LOGGER.error("Permission not allowed");

        return null;
    }

    /**
     * Add new course
     *
     * @param name
     * @return
     */
    @PostMapping(path = "/courses")
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
    @GetMapping(path = "/courses")
    public @ResponseBody
    Object getAllCourses(@RequestHeader String hash) {
        if (authenticate(hash)) {
            return courseService.getAll();
        }
        return "Permission not allowed";
    }

    private Boolean authenticate(String hash) {
        StudentDto student = studentService.getByHash(hash);
        return student != null;
    }
}
