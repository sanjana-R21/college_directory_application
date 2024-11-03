package com.example.leucine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leucine.repository.StudentProfileRepository;
import com.example.leucine.model.StudentProfile;
import java.util.List;

import com.example.leucine.util.ResourceNotFoundException;




@Service
public class StudentProfileService {
    
    @Autowired
    private StudentProfileRepository studentProfile;
    public StudentProfile getProfileByUserId(Long userId) {
        return studentProfile.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user ID: " + userId));
    }
    
    
    public List<StudentProfile> searchStudents(String name, String department, String year) {
    return studentProfile.searchStudents(name, department, year);
}

}
