package com.medicure.microservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    
    @Mock
    private DoctorRepository doctorRepository;
    
    @InjectMocks
    private DoctorService doctorService;
    
    private Doctor testDoctor;
    
    @BeforeEach
    void setUp() {
        testDoctor = new Doctor("DOC001", "Dr. Test", "Cardiology", 
                               "Test City", "1234567890", "test@test.com");
    }
    
    @Test
    void testRegisterDoctor_Success() {
        when(doctorRepository.existsByDoctorRegNo("DOC001")).thenReturn(false);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(testDoctor);
        
        Doctor result = doctorService.registerDoctor(testDoctor);
        
        assertNotNull(result);
        assertEquals("DOC001", result.getDoctorRegNo());
        assertEquals("Dr. Test", result.getDoctorName());
        verify(doctorRepository).save(testDoctor);
    }
    
    @Test
    void testRegisterDoctor_AlreadyExists() {
        when(doctorRepository.existsByDoctorRegNo("DOC001")).thenReturn(true);
        
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> doctorService.registerDoctor(testDoctor));
        
        assertTrue(exception.getMessage().contains("already exists"));
    }
    
    @Test
    void testUpdateDoctor_Success() {
        Doctor updatedDetails = new Doctor();
        updatedDetails.setDoctorName("Dr. Updated");
        updatedDetails.setSpecialization("Neurology");
        
        when(doctorRepository.findByDoctorRegNo("DOC001")).thenReturn(Optional.of(testDoctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(testDoctor);
        
        Doctor result = doctorService.updateDoctor("DOC001", updatedDetails);
        
        assertNotNull(result);
        verify(doctorRepository).save(testDoctor);
    }
    
    @Test
    void testSearchDoctorByName() {
        List<Doctor> doctors = Arrays.asList(testDoctor);
        when(doctorRepository.findByDoctorNameContainingIgnoreCase("Test"))
            .thenReturn(doctors);
        
        List<Doctor> result = doctorService.searchDoctorByName("Test");
        
        assertEquals(1, result.size());
        assertEquals("Dr. Test", result.get(0).getDoctorName());
    }
    
    @Test
    void testDeleteDoctor_Success() {
        when(doctorRepository.existsByDoctorRegNo("DOC001")).thenReturn(true);
        
        assertDoesNotThrow(() -> doctorService.deleteDoctor("DOC001"));
        
        verify(doctorRepository).deleteById("DOC001");
    }
    
    @Test
    void testDeleteDoctor_NotFound() {
        when(doctorRepository.existsByDoctorRegNo("DOC001")).thenReturn(false);
        
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> doctorService.deleteDoctor("DOC001"));
        
        assertTrue(exception.getMessage().contains("not found"));
    }
}