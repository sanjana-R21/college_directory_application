package com.example.leucine.model;

public class FacultyProfileDTO {

    private Long userId;
    private String officeHours;
    private String email;
    private String phone;

    // Constructors
    public FacultyProfileDTO(Long userId, String officeHours, String email, String phone) {
        this.userId = userId;
        this.officeHours = officeHours;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}