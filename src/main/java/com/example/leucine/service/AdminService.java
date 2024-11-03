package com.example.leucine.service;

import com.example.leucine.model.Department;
import com.example.leucine.model.StudentProfile;
import com.example.leucine.model.FacultyProfile;
import com.example.leucine.model.User;
import com.example.leucine.model.Role;
import com.example.leucine.repository.StudentProfileRepository;
import com.example.leucine.repository.FacultyRepository;
import com.example.leucine.repository.UserRepository;
import com.example.leucine.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UserRepository userRepository;

    // CRUD for students
    public List<StudentProfile> getAllStudents() {
        return studentProfileRepository.findAll();
    }

    public void addStudent(StudentProfile studentProfile) {
        // Extract user from the StudentProfile
        User user = studentProfile.getUser();

        if (user != null) {
            // Save the user first
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User information is required for creating a student profile.");
        }

        // Set department and save the student profile
        Department department = new Department();
        department.setId(1L);  // Replace with actual department ID
        studentProfile.setDepartment(department);
        
        // Save the student profile
        studentProfileRepository.save(studentProfile);
    }

    public StudentProfile updateStudent(Long studentId, StudentProfile updatedProfile) {
        StudentProfile student = studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        
        student.setYear(updatedProfile.getYear());
        student.setPhoto(updatedProfile.getPhoto());

        return studentProfileRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentProfileRepository.deleteById(studentId);
    }

    // CRUD for faculty
    public List<FacultyProfile> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public FacultyProfile addFaculty(FacultyProfile facultyProfile) {
        // Create a new User for the faculty member
        User user = facultyProfile.getUser();
        user.setRole(Role.FACULTY_MEMBER);  // Set role as FACULTY_MEMBER

        // Save the user directly without checking for existing email
        userRepository.save(user);

        // Set the department dynamically
        Department department = new Department();
        department.setId(facultyProfile.getDepartment().getId());  // Use the department ID from the request
        facultyProfile.setDepartment(department);

        // Save the faculty profile
        return facultyRepository.save(facultyProfile);
    }

    public FacultyProfile updateFaculty(Long facultyId, FacultyProfile updatedProfile) {
        FacultyProfile faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        
        faculty.setOfficeHours(updatedProfile.getOfficeHours());

        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }
}
