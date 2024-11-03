

package com.example.leucine.repository;

import com.example.leucine.model.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminProfile, Long> {
    Optional<AdminProfile> findByUserId(Long userId);
}
