package com.example.leucine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.leucine.service.StudentProfileService;
import com.example.leucine.model.StudentProfile;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentProfileService studentProfileService;

    @GetMapping("/profile")
    public ResponseEntity<StudentProfile> getStudentProfile(@RequestParam Long userId) {
        StudentProfile profile = studentProfileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profile);  // Ensure this returns enrollments and courses
    }
    
    
    @GetMapping("/search")
    public ResponseEntity<List<StudentProfile>> searchStudents(@RequestParam(required = false) String name) {
        List<StudentProfile> students = studentProfileService.searchStudents(name, null, null);
        return ResponseEntity.ok(students);
    }
    

}
