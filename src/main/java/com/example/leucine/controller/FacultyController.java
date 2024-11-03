package com.example.leucine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.leucine.model.FacultyProfile;
import com.example.leucine.model.StudentProfile;
import com.example.leucine.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/classes")
    public ResponseEntity<List<StudentProfile>> getClassList(@RequestParam Long facultyId) {
        List<StudentProfile> students = facultyService.getStudentsInCourses(facultyId);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/update-profile")
public ResponseEntity<String> updateProfile(@RequestParam Long facultyId, @RequestParam String officeHours,
                                            @RequestParam String email, @RequestParam String phone) {
    FacultyProfile facultyProfile = facultyService.getProfileByUserId(facultyId);
    facultyProfile.setOfficeHours(officeHours);
    facultyProfile.getUser().setEmail(email);
    facultyProfile.getUser().setPhone(phone);
    
    facultyService.updateFacultyProfile(facultyProfile);
    return ResponseEntity.ok("Profile updated successfully");
}


    @GetMapping("/profile")
    public FacultyProfile getProfile(@RequestParam Long userId) {
        return facultyService.getProfileByUserId(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<FacultyProfile> updateFaculty(@RequestBody FacultyProfile updatedFacultyProfile) {
        FacultyProfile updatedProfile = facultyService.updateFacultyProfile(updatedFacultyProfile);
        return ResponseEntity.ok(updatedProfile);
    }
}
