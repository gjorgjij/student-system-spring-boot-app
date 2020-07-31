package com.example.studentsystem.dto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "student")
public class StudentDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Column(name = "email")
    private String email;

    @Column(name = "hash")
    private String hash;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private List<EnrollmentDto> enrollments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<EnrollmentDto> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentDto> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", hash='" + hash + '\'' +
                ", enrollments=" + enrollments +
                '}';
    }
}
