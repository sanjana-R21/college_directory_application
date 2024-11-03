package com.example.leucine.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "student_profile")
public class StudentProfile {

    @Id
    @Column(name = "user_id")
    private Long userId;

    private String photo;
    private String year;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    @OneToMany(mappedBy = "studentProfile", fetch = FetchType.LAZY)
private List<Enrollment> enrollments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user; // Ensure this is mapped correctly.

    // No direct ManyToMany relation with courses
    // Relationships are managed through Enrollment entity

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
