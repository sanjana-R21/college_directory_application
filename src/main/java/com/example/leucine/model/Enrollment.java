package com.example.leucine.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonManagedReference
    private StudentProfile studentProfile;
    

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonManagedReference
    private Course course;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentProfile getStudent() {
        return studentProfile;
    }

    public void setStudent(StudentProfile student) {
        this.studentProfile = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
