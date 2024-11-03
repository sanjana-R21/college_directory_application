package com.example.leucine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.leucine.model.StudentProfile;
import java.util.Optional;
import java.util.List;



@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    
    Optional<StudentProfile> findByUserId(Long userId);

    @Query("SELECT DISTINCT sp FROM StudentProfile sp "
         + "LEFT JOIN FETCH sp.enrollments e "
         + "LEFT JOIN FETCH e.course c "
         + "WHERE (LOWER(sp.user.name) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL) "
         + "AND (LOWER(sp.department.name) = LOWER(:department) OR :department IS NULL) "
         + "AND (sp.year = :year OR :year IS NULL)")
    List<StudentProfile> searchStudents(@Param("name") String name, 
                                        @Param("department") String department, 
                                        @Param("year") String year);
}
