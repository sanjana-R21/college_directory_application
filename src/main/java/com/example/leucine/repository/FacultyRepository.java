package com.example.leucine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.leucine.model.FacultyProfile;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyProfile, Long> {
    Optional<FacultyProfile> findByUserId(Long userId);
}
