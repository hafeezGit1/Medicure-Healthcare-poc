package com.medicure.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;
    
    @PostMapping("/registerDoctor")
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody Doctor doctor) {
        try {
            Doctor savedDoctor = doctorService.registerDoctor(doctor);
            return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ApiResponse("Error", e.getMessage()), 
                HttpStatus.BAD_REQUEST
            );
        }
    }
    
    @PutMapping("/updateDoctor/{doctorRegNo}")
    public ResponseEntity<?> updateDoctor(@PathVariable String doctorRegNo, 
                                        @RequestBody Doctor doctorDetails) {
        try {
            Doctor updatedDoctor = doctorService.updateDoctor(doctorRegNo, doctorDetails);
            return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ApiResponse("Error", e.getMessage()), 
                HttpStatus.NOT_FOUND
            );
        }
    }
    
    @GetMapping("/searchDoctor/{doctorName}")
    public ResponseEntity<?> searchDoctorByName(@PathVariable String doctorName) {
        try {
            List<Doctor> doctors = doctorService.searchDoctorByName(doctorName);
            if (doctors.isEmpty()) {
                return new ResponseEntity<>(
                    new ApiResponse("Info", "No doctors found with name: " + doctorName), 
                    HttpStatus.NOT_FOUND
                );
            }
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                new ApiResponse("Error", "Error searching doctors: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    
    @DeleteMapping("/deletePolicy/{doctorRegNo}")
    public ResponseEntity<?> deleteDoctor(@PathVariable String doctorRegNo) {
        try {
            doctorService.deleteDoctor(doctorRegNo);
            return new ResponseEntity<>(
                new ApiResponse("Success", "Doctor deleted successfully"), 
                HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ApiResponse("Error", e.getMessage()), 
                HttpStatus.NOT_FOUND
            );
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}

class ApiResponse {
    private String status;
    private String message;
    
    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}