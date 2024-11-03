package com.example.leucine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_profile")
public class AdminProfile {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    // Constructors, Getters, and Setters
    public AdminProfile() {}

    public AdminProfile(Long userId, Department department, User user) {
        this.userId = userId;
        this.department = department;
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
