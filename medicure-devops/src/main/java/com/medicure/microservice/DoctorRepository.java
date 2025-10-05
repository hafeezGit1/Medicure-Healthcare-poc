package com.medicure.microservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.doctorName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByDoctorNameContainingIgnoreCase(@Param("name") String name);
    
    Optional<Doctor> findByDoctorRegNo(String doctorRegNo);
    
    boolean existsByDoctorRegNo(String doctorRegNo);
}