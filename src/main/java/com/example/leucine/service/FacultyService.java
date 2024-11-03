package com.example.leucine.service;

import com.example.leucine.model.FacultyProfile;
import com.example.leucine.model.StudentProfile;
import com.example.leucine.model.Enrollment;
import com.example.leucine.repository.EnrollmentRepository;  // You need to create this repository
import com.example.leucine.repository.FacultyRepository;
import com.example.leucine.util.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Method to fetch the list of students enrolled in the faculty's courses
    public List<StudentProfile> getStudentsInCourses(Long facultyId) {
        FacultyProfile faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found for ID: " + facultyId));

        // Fetch all students enrolled in courses taught by the faculty
        return faculty.getCourses().stream()
                .flatMap(course -> enrollmentRepository.findByCourse(course).stream())  // Fetch Enrollments
                .map(Enrollment::getStudent)  // Map to students
                .distinct()  // Ensure each student appears once
                .collect(Collectors.toList());
    }

    public FacultyProfile updateFacultyProfile(FacultyProfile facultyProfile) {
        return facultyRepository.save(facultyProfile);
    }

    public FacultyProfile getProfileByUserId(Long userId) {
        return facultyRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Faculty profile not found for user ID: " + userId));
    }
}
