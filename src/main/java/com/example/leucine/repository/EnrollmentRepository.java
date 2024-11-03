package com.example.leucine.repository;

import com.example.leucine.model.Enrollment;
import com.example.leucine.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByCourse(Course course);  // Find all enrollments for a given course
}
