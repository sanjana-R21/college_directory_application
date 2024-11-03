package com.example.leucine.controller;

import com.example.leucine.model.AdminProfile;
import com.example.leucine.model.StudentProfile;
import com.example.leucine.model.FacultyProfile;
import com.example.leucine.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // CRUD for Students
    @GetMapping("/students")
    public ResponseEntity<List<StudentProfile>> getAllStudents() {
        return ResponseEntity.ok(adminService.getAllStudents());
    }

    @PostMapping("/students")
    public ResponseEntity<?> addStudent(@RequestBody StudentProfile studentProfile) {
        adminService.addStudent(studentProfile);
        return ResponseEntity.ok("Student added successfully");
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentProfile> updateStudent(@PathVariable Long id, @RequestBody StudentProfile studentProfile) {
        return ResponseEntity.ok(adminService.updateStudent(id, studentProfile));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        adminService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // CRUD for Faculty
    @GetMapping("/faculty")
    public ResponseEntity<List<FacultyProfile>> getAllFaculty() {
        return ResponseEntity.ok(adminService.getAllFaculty());
    }

    @PostMapping("/faculty")
    public ResponseEntity<FacultyProfile> addFaculty(@RequestBody FacultyProfile facultyProfile) {
        return ResponseEntity.ok(adminService.addFaculty(facultyProfile));
    }

    @PutMapping("/faculty/{id}")
    public ResponseEntity<FacultyProfile> updateFaculty(@PathVariable Long id, @RequestBody FacultyProfile facultyProfile) {
        return ResponseEntity.ok(adminService.updateFaculty(id, facultyProfile));
    }

    @DeleteMapping("/faculty/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {
        adminService.deleteFaculty(id);
        return ResponseEntity.ok("Faculty deleted successfully");
    }
    
    // Dashboard API (placeholder, to be implemented)
    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboardData() {
        return ResponseEntity.ok("Dashboard data (placeholder)");
    }
}
