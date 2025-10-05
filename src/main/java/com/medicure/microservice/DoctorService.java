package com.medicure.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    public Doctor registerDoctor(Doctor doctor) {
        if (doctorRepository.existsByDoctorRegNo(doctor.getDoctorRegNo())) {
            throw new RuntimeException("Doctor with registration number " + 
                                     doctor.getDoctorRegNo() + " already exists");
        }
        return doctorRepository.save(doctor);
    }
    
    public Doctor updateDoctor(String doctorRegNo, Doctor doctorDetails) {
        Optional<Doctor> existingDoctor = doctorRepository.findByDoctorRegNo(doctorRegNo);
        if (existingDoctor.isEmpty()) {
            throw new RuntimeException("Doctor with registration number " + 
                                     doctorRegNo + " not found");
        }
        
        Doctor doctor = existingDoctor.get();
        if (doctorDetails.getDoctorName() != null) {
            doctor.setDoctorName(doctorDetails.getDoctorName());
        }
        if (doctorDetails.getSpecialization() != null) {
            doctor.setSpecialization(doctorDetails.getSpecialization());
        }
        if (doctorDetails.getLocation() != null) {
            doctor.setLocation(doctorDetails.getLocation());
        }
        if (doctorDetails.getMobileNumber() != null) {
            doctor.setMobileNumber(doctorDetails.getMobileNumber());
        }
        if (doctorDetails.getEmail() != null) {
            doctor.setEmail(doctorDetails.getEmail());
        }
        
        return doctorRepository.save(doctor);
    }
    
    public List<Doctor> searchDoctorByName(String doctorName) {
        return doctorRepository.findByDoctorNameContainingIgnoreCase(doctorName);
    }
    
    public void deleteDoctor(String doctorRegNo) {
        if (!doctorRepository.existsByDoctorRegNo(doctorRegNo)) {
            throw new RuntimeException("Doctor with registration number " + 
                                     doctorRegNo + " not found");
        }
        doctorRepository.deleteById(doctorRegNo);
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}