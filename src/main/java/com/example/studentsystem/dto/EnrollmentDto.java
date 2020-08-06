package com.example.studentsystem.dto;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="enrollment")
public class EnrollmentDto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    private int courseId;
    private int studentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int course_id) {
        this.courseId = course_id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int student_id) {
        this.studentId = student_id;
    }

    @Override
    public String toString() {
        return "EnrollmentDTO{" +
                "id=" + id +
                '}';
    }
}
