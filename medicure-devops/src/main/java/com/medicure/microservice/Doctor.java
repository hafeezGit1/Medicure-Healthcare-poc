package com.medicure.microservice;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @Column(name = "doctor_reg_no", unique = true, nullable = false)
    @JsonProperty("doctorRegNo")
    private String doctorRegNo;
    
    @Column(name = "doctor_name", nullable = false)
    @NotBlank(message = "Doctor name is required")
    @JsonProperty("doctorName")
    private String doctorName;
    
    @Column(name = "specialization")
    @JsonProperty("specialization")
    private String specialization;
    
    @Column(name = "location")
    @JsonProperty("location")
    private String location;
    
    @Column(name = "mobile_number")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be 10 digits")
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    
    @Column(name = "email")
    @Email(message = "Invalid email format")
    @JsonProperty("email")
    private String email;
    
    // Default constructor
    public Doctor() {}
    
    // Constructor with parameters
    public Doctor(String doctorRegNo, String doctorName, String specialization, 
                  String location, String mobileNumber, String email) {
        this.doctorRegNo = doctorRegNo;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.location = location;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }
    
    // Getters and Setters
    public String getDoctorRegNo() { return doctorRegNo; }
    public void setDoctorRegNo(String doctorRegNo) { this.doctorRegNo = doctorRegNo; }
    
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    @Override
    public String toString() {
        return "Doctor{" +
                "doctorRegNo='" + doctorRegNo + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", location='" + location + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}