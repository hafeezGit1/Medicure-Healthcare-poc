package com.medicure.microservice;

import org.testng.annotations.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import static org.testng.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorControllerTestNG extends AbstractTestNGSpringContextTests {
    
    @LocalServerPort
    private int port;
    
    private TestRestTemplate restTemplate;
    private String baseUrl;
    
    @BeforeClass
    public void setUp() {
        restTemplate = new TestRestTemplate();
        baseUrl = "http://localhost:" + port + "/medicure/api/doctors";
    }
    
    @Test(priority = 1)
    public void testRegisterDoctor() {
        Doctor doctor = new Doctor("DOC999", "Dr. TestNG", "Surgery", 
                                  "Test Location", "9999999999", "testng@test.com");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Doctor> request = new HttpEntity<>(doctor, headers);
        
        ResponseEntity<Doctor> response = restTemplate.postForEntity(
            baseUrl + "/registerDoctor", request, Doctor.class);
        
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getDoctorRegNo(), "DOC999");
    }
    
    @Test(priority = 2, dependsOnMethods = "testRegisterDoctor")
    public void testSearchDoctor() {
        ResponseEntity<Doctor[]> response = restTemplate.getForEntity(
            baseUrl + "/searchDoctor/TestNG", Doctor[].class);
        
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }
    
    @Test(priority = 3, dependsOnMethods = "testRegisterDoctor")
    public void testUpdateDoctor() {
        Doctor updateData = new Doctor();
        updateData.setSpecialization("Updated Surgery");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Doctor> request = new HttpEntity<>(updateData, headers);
        
        ResponseEntity<Doctor> response = restTemplate.exchange(
            baseUrl + "/updateDoctor/DOC999", HttpMethod.PUT, request, Doctor.class);
        
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getSpecialization(), "Updated Surgery");
    }
    
    @Test(priority = 4, dependsOnMethods = {"testRegisterDoctor", "testUpdateDoctor"})
    public void testDeleteDoctor() {
        ResponseEntity<String> response = restTemplate.exchange(
            baseUrl + "/deletePolicy/DOC999", HttpMethod.DELETE, null, String.class);
        
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    
    @Test(priority = 5)
    public void testGetAllDoctors() {
        ResponseEntity<Doctor[]> response = restTemplate.getForEntity(
            baseUrl + "/all", Doctor[].class);
        
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }
}