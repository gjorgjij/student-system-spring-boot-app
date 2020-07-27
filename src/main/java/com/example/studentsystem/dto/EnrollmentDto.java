package com.example.studentsystem.dto;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="enrollment")
public class EnrollmentDto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    private int course_id;
    private int student_id;

//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private StudentDTO studentDTO;
//
//    @ManyToOne
//    @JoinColumn(name="course_id")
//    private CourseDTO courseDTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "EnrollmentDTO{" +
                "id=" + id +
                '}';
    }
}
