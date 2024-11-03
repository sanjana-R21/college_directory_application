package com.example.leucine.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;


@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    @JsonBackReference  // Prevent circular reference with FacultyProfile
    private FacultyProfile faculty;

    // No direct ManyToMany relation with students, handled by Enrollment

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public FacultyProfile getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyProfile faculty) {
        this.faculty = faculty;
    }
}
