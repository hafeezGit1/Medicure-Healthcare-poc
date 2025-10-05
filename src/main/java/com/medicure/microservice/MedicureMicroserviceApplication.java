package com.medicure.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class MedicureMicroserviceApplication implements CommandLineRunner {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(MedicureMicroserviceApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Preload some test data
        if (doctorRepository.count() == 0) {
            doctorRepository.save(new Doctor("DOC001", "Dr. John Smith", 
                "Cardiology", "New York", "1234567890", "john.smith@medicure.com"));
            doctorRepository.save(new Doctor("DOC002", "Dr. Sarah Johnson", 
                "Pediatrics", "California", "9876543210", "sarah.johnson@medicure.com"));
            System.out.println("Sample doctors loaded into database");
        }
    }
}